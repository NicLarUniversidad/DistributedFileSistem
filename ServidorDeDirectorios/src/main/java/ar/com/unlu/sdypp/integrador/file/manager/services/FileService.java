package ar.com.unlu.sdypp.integrador.file.manager.services;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.FileCrud;
import ar.com.unlu.sdypp.integrador.file.manager.cruds.FilePartCrud;
import ar.com.unlu.sdypp.integrador.file.manager.cruds.UserCrud;
import ar.com.unlu.sdypp.integrador.file.manager.exceptions.FileClosedException;
import ar.com.unlu.sdypp.integrador.file.manager.models.FileListModel;
import ar.com.unlu.sdypp.integrador.file.manager.models.FileLogsModel;
import ar.com.unlu.sdypp.integrador.file.manager.models.FileModel;
import ar.com.unlu.sdypp.integrador.file.manager.models.PartsModel;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.AsyncFileRepository;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.FileDataRepository;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.FileRepository;
import ar.com.unlu.sdypp.integrador.file.manager.utils.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class FileService {

    public static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public final String TEMP_DIRECTORY = "/tmp/";
    public static final Integer PART_NUMBERS = 10;

    @Value("${sdypp.file.server.host}")
    private String host;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileDataRepository fileDataRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TimeLogService timeLogService;

    public FileCrud save(MultipartFile file, String username, Integer id, Boolean append) throws Exception {
        var fileData = new FileCrud();
        if (id == 0) {
            var user = this.userService.findByUsername(username);
            if (user == null) {
                user = new UserCrud();
                user.setUsername(username);
            }
            fileData.setActivo(true);
            fileData.setNombreArchivo(file.getOriginalFilename());
            fileData.setTamaño2(0);
            fileData.setUser(user);
            fileData.setState(FileCrud.UNLOCKED);
            this.fileDataRepository.save(fileData);
            this.createPart(file, username, fileData, append);
        }
        else {
            var fileDataOpt = this.fileDataRepository.findById(id);
            if (fileDataOpt.isPresent()) {
                fileData = fileDataOpt.get();
                this.createPart(file, username, fileData, append);
            }
            else {
                throw new FileNotFoundException(String.format("File with id = [{}] not found", id));
            }
        }
        return fileData;
    }

    public Resource getFile(Integer fileId) throws IOException, InterruptedException {
        var fileDataOpt = this.fileDataRepository.findById(fileId);
        if (fileDataOpt.isPresent()) {
            var fileData = fileDataOpt.get();
            var threads = new HashMap<Integer, AsyncFileRepository>();
            var fileDownloadLog = this.timeLogService.startFileDownload(fileData);
            logger.info("Iniciando descarga archivo: [{}], en hilo principal: [{}]", fileData.getNombreArchivo(), Thread.currentThread().getName());
            var initTime = System.currentTimeMillis();
            for (FilePartCrud part : fileData.getParts()) {
                AsyncFileRepository asyncFileRepository = new AsyncFileRepository(part.getNombre(), fileData.getUser().getUsername(), this.host);
                asyncFileRepository.start();
                threads.put(part.getOrden(), asyncFileRepository);
            }
            var contentMap = new HashMap<Integer, byte[]>();
            for(Integer key : threads.keySet()) {
                AsyncFileRepository thread = threads.get(key);
                thread.join();
                contentMap.put(key, thread.getFileContent());
                this.timeLogService.logPartDownload(
                        fileDownloadLog,
                        thread.getStartTime(),
                        thread.getEndTime(),
                        thread.getFileId()
                );
            }
            var finishTime = System.currentTimeMillis();
            logger.info("Descarga finalizada, tiempo de descarga en hilo principal: {}", initTime - finishTime);
            this.timeLogService.finishFileDownload(fileDownloadLog);
            Integer i = 1;
            StringBuilder fileContent = new StringBuilder();
            while (i <= contentMap.size()) {
                var content = new String(contentMap.get(i));
                fileContent.append(content);
                i++;
            }
            String fileName = fileData.getNombreArchivo();
            File temp = new File(TEMP_DIRECTORY);
            if (!temp.exists())
                temp.mkdir();
            File file = new File(TEMP_DIRECTORY + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file);
            out.write(fileContent.toString().getBytes());
            return new InputStreamResource(new FileInputStream(file));

        }

        return null;
    }

    //TODO: Si tiene varias partes, acá se podrían ir recuperando y juntando
    public File join(List<File> list) throws IOException {
        File outPutFile = File.createTempFile("temp-", "-unsplit", new File(TEMP_DIRECTORY));
        FileOutputStream fos = new FileOutputStream(outPutFile);
        for (File file : list) {
            Files.copy(file.toPath(), fos);
        }
        fos.close();
        return outPutFile;
    }


    public FileCrud uploadFile(MultipartFile file, String username, Integer id, Boolean append) throws Exception {
        //Dividir el archivo en partes
        //Subir las partes a rabbit
        //Verificar que todas las partes se hayan guardado (Opcional)
        return this.save(file, username, id, append);
    }

    //publicar archivo en rabbit
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void publishFile(String filePath, String queueName) throws IOException {
        // Leer el contenido del archivo
        Path path = Paths.get(filePath);
        byte[] fileContent = Files.readAllBytes(path);
        // Configurar las propiedades del mensaje
        MessageProperties properties = new MessageProperties();
        properties.setContentType("application/octet-stream"); // Tipo MIME para el contenido binario
        // Crear el mensaje con el contenido del archivo y las propiedades
        Message message = new Message(fileContent, properties);
        // Publicar el mensaje en RabbitMQ
        rabbitTemplate.send(queueName, message);
        System.out.println("Archivo " + filePath + " publicado en la cola " + queueName);
    }

    public FileListModel getAllFiles(String username, Pageable pageable) {
        Page<FileCrud> files = this.fileDataRepository.findAllByUserUsernameAndActivo(username, true, pageable);
        FileListModel fileListModel = new FileListModel();
        fileListModel.setFiles(files);
        return fileListModel;
    }

    public void deleteFile(Integer fileId) {
        var opt = this.fileDataRepository.findById(fileId);
        if (opt.isPresent()) {
            var fileData = opt.get();
            fileData.setActivo(false);
            this.fileDataRepository.save(fileData);
        }
    }

    public PartsModel getParts(Integer fileId) {
        PartsModel partsModel = new PartsModel();
        var file = this.fileDataRepository.findById(fileId);
        if (file.isPresent()) {
            partsModel.setParts(file.get().getParts());
        }
        return partsModel;
    }

    public FileLogsModel getFileLogs(Integer fileId) throws Exception {
        var fileData = this.fileDataRepository.findById(fileId);
        if (fileData.isPresent()) {
            return this.timeLogService.getFileLogs(fileData.get().getNombreArchivo());
        }
        throw new Exception("No se encontró el archivo con id " +fileId);
    }

    public FileCrud updateFile(Integer fileId, MultipartFile file, String username) throws Exception {
        var fileMetadataOpt = this.fileDataRepository.findById(fileId);
        if (fileMetadataOpt.isPresent()) {
            var fileMetadata = fileMetadataOpt.get();
            if (isFileLocked(fileMetadata)) {
                throw new FileClosedException(String.format("Archivo con ID = '{}' y nombre '{}' bloqueado", fileId,
                        fileMetadata.getNombreArchivo()));
            }
            var parts = fileMetadata.getParts();
            var newParts = this.fileRepository.splitByNumberOfFiles(file, PART_NUMBERS);
            int count = 1;
            while (count <= parts.size()) {
                for (var part: parts) {
                    if (part.getOrden() == count){
                        this.fileRepository.save(newParts.get(count - 1), username, part.getNombre(), FileModel.MODIFICACION);
                    }
                }
                count++;
            }
            fileMetadata.setTamaño2((int) file.getSize());
            fileMetadata.setTamaño(file.getSize() + " bytes");
            fileMetadata.setState(FileCrud.UNLOCKED);
            this.fileDataRepository.save(fileMetadata);
            return fileMetadata;
        }
        else {
            throw new Exception("No se encontró el archivo con ID=" + fileId);
        }
    }

    public FileCrud lockFile(Integer fileId) throws Exception {
        var fileMetadataOpt = this.fileDataRepository.findById(fileId);
        if (fileMetadataOpt.isPresent()) {
            var fileMetadata = fileMetadataOpt.get();
            if (fileMetadata.getState() == FileCrud.UNLOCKED) {
                fileMetadata.setState(FileCrud.LOCKED);
                fileMetadata.setLastTimeOpen(Time.getCurrentTime());
                this.fileDataRepository.save(fileMetadata);
                return fileMetadata;
            }
            else {
                if (isFileLocked(fileMetadata)) {
                    throw new FileClosedException(String.format("Archivo con ID = '{}' y nombre '{}' bloqueado", fileId,
                            fileMetadata.getNombreArchivo()));
                }
                fileMetadata.setLastTimeOpen(Time.getCurrentTime());
                this.fileDataRepository.save(fileMetadata);
                return fileMetadata;
            }
        }
        else {
            throw new Exception("No se encontró el archivo con ID=" + fileId);
        }
    }

    private Boolean isFileLocked(FileCrud fileCrud) {
        boolean isLocked = false;
        if (fileCrud.getState() == FileCrud.LOCKED) {
            isLocked = Time.getCurrentTime()
                    .after(new Date(fileCrud.getLastTimeOpen().getTime() + TimeUnit.MINUTES.toMillis(5)));
        }
        return isLocked;
    }

    public FileCrud getFileData(Integer fileId) {
        FileCrud fileCrud = null;
        var fileOptional = this.fileDataRepository.findById(fileId);
        if (fileOptional.isPresent()) {
            fileCrud = fileOptional.get();
        }
        return fileCrud;
    }

    public void deleteLogs(Integer fileId) throws Exception {
        var fileData = this.fileDataRepository.findById(fileId);
        if (fileData.isPresent()) {
            this.timeLogService.deleteLogs(fileData.get().getNombreArchivo());
        }
        throw new Exception("No se encontró el archivo con id " +fileId);
    }

    private void createPart(MultipartFile file, String username, FileCrud fileData, Boolean append) throws Exception {
        if (fileData.getOpenToAppend()) {
            FilePartCrud filePartCrud = new FilePartCrud();
            filePartCrud.setOriginalFile(fileData);
            filePartCrud.setOrden(fileData.getParts().size() + 1);
            filePartCrud.setNombre(UUID.randomUUID() + ".part" + filePartCrud.getOrden());
            fileData.getParts().add(filePartCrud);
            fileData.setTamaño2((int) (file.getSize() + fileData.getTamaño2()));
            fileData.setTamaño(fileData.getTamaño2() + " bytes");
            fileData.setOpenToAppend(append);
            this.fileRepository.save(file, username, filePartCrud.getNombre());
        } else {
            throw new FileClosedException(String.format("No se pueden seguir agregando partes al archivo con id=[{}]", fileData.getID()));
        }
    }

}

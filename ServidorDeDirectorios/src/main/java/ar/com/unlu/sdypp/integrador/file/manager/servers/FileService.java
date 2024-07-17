package ar.com.unlu.sdypp.integrador.file.manager.servers;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.FileCrud;
import ar.com.unlu.sdypp.integrador.file.manager.cruds.FilePartCrud;
import ar.com.unlu.sdypp.integrador.file.manager.models.FileListModel;
import ar.com.unlu.sdypp.integrador.file.manager.models.PartsModel;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.FileDataRepository;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    public static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public final String TEMP_DIRECTORY = "/tmp/";
    public static final Integer PART_NUMBERS = 10;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileDataRepository fileDataRepository;
    @Autowired
    private UserService userService;

    public FileCrud save(MultipartFile file, String username) throws IOException {
        var parts = this.fileRepository.splitByNumberOfFiles(file, PART_NUMBERS);
        var fileData = new FileCrud();
        var user = this.userService.findByUsername(username);
        //user.setUsername(username);
        fileData.setActivo(true);
        fileData.setNombreArchivo(file.getOriginalFilename());
        fileData.setTamaño(file.getSize() + " bytes");
        fileData.setTamaño2((int) file.getSize());
        fileData.setUser(user);
        int count = 1;
        for (var part : parts) {
            var partData = new FilePartCrud();
            partData.setOrden(count);
            String partName = UUID.randomUUID() + ".temp";
            partData.setNombre(partName);
            partData.setOriginalFile(fileData);
            fileData.getParts().add(partData);
            count++;
            this.fileRepository.save(part, username, partName);
        }
        this.fileDataRepository.save(fileData);
        return fileData;
    }

    public Resource getFile(Integer fileId) throws IOException {
        //TODO: Si tiene varias partes, acá se podrían ir recuperando y juntando
        var fileDataOpt = this.fileDataRepository.findById(fileId);
        if (fileDataOpt.isPresent()) {
            var fileData = fileDataOpt.get();
            var contentMap = new HashMap<Integer, byte[]>();
            for (FilePartCrud part : fileData.getParts()) {
                logger.info(part.getNombre() + " " + part.getOrden() + " " + part.getId() + " " + fileData.getUser().getUsername());
                String carpeta = fileData.getUser().getUsername();
                String nombreArchivo = part.getNombre(); //Archivo
                byte[] file = this.fileRepository.getFile(nombreArchivo, carpeta);
                contentMap.put(part.getOrden(), file);
                logger.info("Recived part with {} bytes", file.length);
            }
            byte[] contenidoArchivo = new byte[fileData.getTamaño2()];
            int i = 0;
            for (var part : contentMap.entrySet()) {
                for (byte oneByte : part.getValue()) {
                    contenidoArchivo[i] = oneByte;
                    i++;
                }
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
            out.write(contenidoArchivo);
            return new InputStreamResource(new FileInputStream(file));

        }

        return null; //this.fileRepository.getFile(fileId);
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


    public FileCrud uploadFile(MultipartFile file, String username) throws IOException {
        //Dividir el archivo en partes
        //Subir las partes a rabbit
        //Verificar que todas las partes se hayan guardado (Opcional)
        return this.save(file, username);
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

    public FileListModel getAllFiles(String username) {
        List<FileCrud> files = this.fileDataRepository.findAllByUserUsernameAndActivo(username, true);
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
}

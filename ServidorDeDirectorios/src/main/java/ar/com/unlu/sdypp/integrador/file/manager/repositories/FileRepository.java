package ar.com.unlu.sdypp.integrador.file.manager.repositories;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.FileCrud;
import ar.com.unlu.sdypp.integrador.file.manager.models.FileModel;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.amqp.RabbitmqRepository;
import ar.com.unlu.sdypp.integrador.file.manager.servers.LoadBalancerService;
import ar.com.unlu.sdypp.integrador.file.manager.servers.UserService;
import ar.com.unlu.sdypp.integrador.file.manager.utils.json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileRepository {

    public final static String TEMP_DIRECTORY = "/";

    //@Autowired
    private LoadBalancerService loadBalancerService;
    private final RabbitmqRepository rabbitmqRepository;
    private FileDataRepository fileDataRepository;
    private json jsonConverter;

    @Value("${sdypp.file.server.host:http://localhost:8081}")
    private String host;

    @Autowired
    public FileRepository(RabbitmqRepository rabbitmqRepository, FileDataRepository fileDataRepository, json jsonConverter) {
        this.rabbitmqRepository = rabbitmqRepository;
        this.fileDataRepository = fileDataRepository;
        this.jsonConverter = new json();
    }

    public void save(MultipartFile file, String username) throws IOException {
        //Se guarda la información del archivo
        FileCrud newFileCrud = new FileCrud();
        FileModel user = new FileModel();
        newFileCrud.setActivo(true);
        newFileCrud.setTamaño(file.getSize() + " bytes");
        newFileCrud.setTamaño2((int) file.getSize());
        newFileCrud.setNombreArchivo(file.getName());
        String[] parts = file.getName().split("\\.");
        newFileCrud.setTipo(parts[parts.length - 1]);
        fileDataRepository.save(newFileCrud);

        //TODO: Dividir en partes el archivo y guardar en la base información de cada parte
        //Y publicar cada parte por separado

        //Se publica en rabbit
        user.setName(file.getOriginalFilename());
        user.setContent(file.getBytes());
        user.setUsername(username);
        user.setSize(file.getSize());
        rabbitmqRepository.send(jsonConverter.ConvertirAjson(user));

    }

    public void save(File file, String username, String partName) throws IOException {
        //Se guarda la información del archivo
        //FileCrud newFileCrud = new FileCrud();
        FileModel fileModel = new FileModel();
        //newFileCrud.setActivo(true);
        //newFileCrud.setTamaño((int) file.length());
        //newFileCrud.setNombreArchivo(file.getName());
        String[] parts = file.getName().split("\\.");
        //newFileCrud.setTipo(parts[parts.length - 1]);
        //fileDataRepository.save(newFileCrud);
        //Y publicar cada parte por separado

        //Se publica en rabbit
        var content = Files.readAllBytes(file.toPath());
        fileModel.setName(partName);
        fileModel.setContent(content);
        fileModel.setUsername(username);
        fileModel.setSize(content.length);
        rabbitmqRepository.send(jsonConverter.ConvertirAjson(fileModel));

    }

    //Divide el archivo en partes
    public List<java.io.File> splitBySize(MultipartFile largeFile, int maxChunkSize) throws IOException {
        List<java.io.File> list = new ArrayList<>();
        try (InputStream in = largeFile.getInputStream()) {
            final byte[] buffer = new byte[maxChunkSize];
            int dataRead = in.read(buffer);
            while (dataRead > -1) {
                java.io.File fileChunk = stageFile(buffer, dataRead);
                list.add(fileChunk);
                dataRead = in.read(buffer);
            }
        }
        return list;
    }


    private java.io.File stageFile(byte[] buffer, int length) throws IOException {
//        UUID uuid = UUID.randomUUID();
//        File newFile = new File(TEMP_DIRECTORY + uuid + ".temp");
//        if (!newFile.exists()) {
//            newFile.createNewFile();
//        }
        java.io.File outPutFile = java.io.File.createTempFile("temp-", "-split", new java.io.File(TEMP_DIRECTORY));
        try(FileOutputStream fos = new FileOutputStream(outPutFile)) {
            fos.write(buffer, 0, length);
        }
        return outPutFile;
    }


    private int getSizeInBytes(long totalBytes, int numberOfFiles) {
        if (totalBytes % numberOfFiles != 0) {
            totalBytes = ((totalBytes / numberOfFiles) + 1)*numberOfFiles;
        }
        long x = totalBytes / numberOfFiles;
        if (x > Integer.MAX_VALUE){
            throw new NumberFormatException("Byte chunk too large");

        }
        return (int) x;
    }

    public List<java.io.File> splitByNumberOfFiles(MultipartFile largeFile, int noOfFiles) throws IOException {
        return splitBySize(largeFile, getSizeInBytes(largeFile.getSize(), noOfFiles));
    }

    //junto los pedazos del archivo
    public java.io.File join(List<java.io.File> list) throws IOException {
        java.io.File outPutFile = java.io.File.createTempFile("temp-", "unsplit", new java.io.File("TEMP_DIRECTORY"));
        FileOutputStream fos = new FileOutputStream(outPutFile);
        for (java.io.File file : list) {
            Files.copy(file.toPath(), fos);
        }
        fos.close();
        return outPutFile;
    }

    public byte[] getFile(String fileId, String username) {


        RestTemplate restTemplate = new RestTemplate();

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "/file?username=" +  username + "&id=" + fileId);

        var requestEntity = new HttpEntity<>(map);
        HttpEntity<byte[]> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                byte[].class);

        return response.getBody();
    }
}

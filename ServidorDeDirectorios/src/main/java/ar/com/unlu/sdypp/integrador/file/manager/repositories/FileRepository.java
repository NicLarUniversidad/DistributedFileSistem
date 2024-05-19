package ar.com.unlu.sdypp.integrador.file.manager.repositories;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.File;
import ar.com.unlu.sdypp.integrador.file.manager.models.FileModel;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.amqp.RabbitmqRepository;
import ar.com.unlu.sdypp.integrador.file.manager.servers.LoadBalancerService;
import ar.com.unlu.sdypp.integrador.file.manager.utils.json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;

@Component
public class FileRepository {

    //@Autowired
    private LoadBalancerService loadBalancerService;
    private final RabbitmqRepository rabbitmqRepository;
    private FileDataRepository fileDataRepository;
    private json jsonConverter;


    @Autowired
    public FileRepository(RabbitmqRepository rabbitmqRepository, FileDataRepository fileDataRepository, json jsonConverter) {
        this.rabbitmqRepository = rabbitmqRepository;
        this.fileDataRepository = fileDataRepository;
        this.jsonConverter = new json();
    }

    public void save(MultipartFile file, String username) throws IOException {
        File newFile = new File();
        FileModel user = new FileModel();
        newFile.setActivo(true);
        newFile.setTamaño(file.getSize() + " bytes");
        newFile.setNombreArchivo(file.getName());
        String[] parts = file.getName().split("\\.");
        newFile.setTipo(parts[parts.length - 1]);
        fileDataRepository.save(newFile);
        user.setName(file.getName());
        user.setContent(new String(file.getBytes()));
        user.setUsername(username);
        user.setSize(file.getSize());
        rabbitmqRepository.send(jsonConverter.ConvertirAjson(user));

    }

    public MultipartFile getFile(String fileId) {


        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String boundary = Long.toHexString(System.currentTimeMillis());

        LinkedMultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.add("Content-disposition", "form-data; file-id=" + fileId);
        //pdfHeaderMap.add("Content-type", "application/json; boundary =" + boundary);

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:10000/get-file/" + fileId);

        var requestEntity = new HttpEntity<>(map, headers);
        HttpEntity<MultipartFile> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                MultipartFile.class);

        return response.getBody();
    }
}

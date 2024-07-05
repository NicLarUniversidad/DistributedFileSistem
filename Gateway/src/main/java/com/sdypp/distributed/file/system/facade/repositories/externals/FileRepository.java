package com.sdypp.distributed.file.system.facade.repositories.externals;

import com.sdypp.distributed.file.system.facade.entities.UserEntity;
import com.sdypp.distributed.file.system.facade.models.FileDetailsModel;
import com.sdypp.distributed.file.system.facade.models.FilesDetailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.io.IOException;

@Component
public class FileRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileRepository.class);

    @Value("${sdypp.file.server.host:http://localhost:10000/}")
    private String host;

    public FilesDetailModel getAllFiles(String username) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "files?username="
                + username);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<FilesDetailModel> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                FilesDetailModel.class);
        return response.getBody();
    }

    public FileDetailsModel uploadFile(MultipartFile file, String currentUser) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        String boundary = Long.toHexString(System.currentTimeMillis());
        LinkedMultiValueMap<String, String> pdfHeaderMap = new LinkedMultiValueMap<>();
        pdfHeaderMap.add("Content-disposition", "form-data; name=file; filename=" + file.getOriginalFilename());
        pdfHeaderMap.add("Content-type", "multipart/form-data; boundary =" + boundary);
        HttpEntity<byte[]> doc = new HttpEntity<>(file.getBytes(), pdfHeaderMap);

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", doc);
        map.add("username", currentUser);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "upload-file");

        logger.info("Se hace una solicitud al servicio [Gestor de archivos]");

        var requestEntity = new HttpEntity<>(map, headers);
        HttpEntity<FileDetailsModel> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                FileDetailsModel.class);

        logger.info("Se devolvió desde el servicio [Gestor de archivos]: " + response.getBody());

        return response.getBody();
    }

    public byte[] getFile(String fileId) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String boundary = Long.toHexString(System.currentTimeMillis());

        LinkedMultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.add("Content-disposition", "form-data; file-id=" + fileId);
        //pdfHeaderMap.add("Content-type", "pplication/json; boundary =" + boundary);

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "get-file/" + fileId);

        logger.info("Se hace una solicitud al servicio [Gestor de archivos]");

        var requestEntity = new HttpEntity<>(map, headers);
        HttpEntity<byte[]> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                byte[].class);

        logger.info("Se devolvió desde el servicio [Gestor de archivos]: " + response.getBody());

        return response.getBody();
    }
}

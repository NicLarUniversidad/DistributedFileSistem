package com.sdypp.distributed.file.system.facade.repositories.externals;

import com.sdypp.distributed.file.system.facade.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
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
import java.util.UUID;

@Component
public class FileRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileRepository.class);

    @Value("${sdypp.file.server.host:http://localhost:10000/}")
    private String host;

    public FilesDetailModel getAllFiles(String username, Pageable pageable) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String parameters = String.format("?page=%d&size=%d", pageable.getPageNumber(), pageable.getPageSize());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "files/"
                + username + parameters);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<FilesDetailModel> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                FilesDetailModel.class);
        return response.getBody();
    }

    public FileModel uploadFile(MultipartFile file, String currentUser, Integer id, Boolean append) throws IOException {
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
        map.add("x-chunk", id);
        map.add("chunk-append", append);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "upload-file");

        logger.info("Se hace una solicitud al servicio [Gestor de archivos]");

        var requestEntity = new HttpEntity<>(map, headers);
        HttpEntity<FileModel> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                FileModel.class);

        logger.info("Se devolvió desde el servicio [Gestor de archivos]: " + response.getBody());

        return response.getBody();
    }

    public Resource getFile(String fileId) {

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
        HttpEntity<Resource> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                Resource.class);

        logger.info("Se devolvió desde el servicio [Gestor de archivos]: " + response.getBody());

        return response.getBody();
    }

    public String deleteFile(Integer fileId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "delete-file/" + fileId);

        logger.info("Se hace una solicitud al servicio [Gestor de archivos]");

        var requestEntity = new HttpEntity<>(map, headers);
        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.DELETE,
                requestEntity,
                String.class);

        return response.getBody();
    }

    public PartModels getFileParts(Integer fileId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "file/parts/" + fileId);

        logger.info("Se hace una solicitud al servicio [Gestor de archivos]");

        var requestEntity = new HttpEntity<>(map, headers);
        HttpEntity<PartModels> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                PartModels.class);

        return response.getBody();
    }

    public FileLogsModel getFileLogs(Integer fileId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "file/log/" + fileId);

        logger.info("Se hace una solicitud al servicio [Gestor de archivos]");

        var requestEntity = new HttpEntity<>(map, headers);
        HttpEntity<FileLogsModel> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                FileLogsModel.class);

        return response.getBody();
    }

    public FileModel updateFile(String newText, String currentUser, String fileId) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        String boundary = Long.toHexString(System.currentTimeMillis());
        LinkedMultiValueMap<String, String> pdfHeaderMap = new LinkedMultiValueMap<>();
        pdfHeaderMap.add("Content-disposition", "form-data; name=file; filename=" + UUID.randomUUID() + ".temp");
        pdfHeaderMap.add("Content-type", "multipart/form-data; boundary =" + boundary);
        HttpEntity<byte[]> doc = new HttpEntity<>(newText.getBytes(), pdfHeaderMap);
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", doc);
        map.add("username", currentUser);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "update-file/" + fileId);

        logger.info("Se hace una solicitud al servicio [Gestor de archivos]");

        var requestEntity = new HttpEntity<>(map, headers);
        HttpEntity<FileModel> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                FileModel.class);

        logger.info("Se devolvió desde el servicio [Gestor de archivos]: " + response.getBody());

        return response.getBody();
    }

    public FileModel getFileData(Integer fileId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "file/data/" + fileId);

        logger.info("Se hace una solicitud al servicio [Gestor de archivos]");

        var requestEntity = new HttpEntity<>(map, headers);
        HttpEntity<FileModel> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                FileModel.class);

        return response.getBody();
    }

    public FileModel lockFile(Integer fileId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "file/lock/" + fileId);

        logger.info("Se hace una solicitud al servicio [Gestor de archivos]");

        var requestEntity = new HttpEntity<>(map, headers);
        HttpEntity<FileModel> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                FileModel.class);

        return response.getBody();
    }

    public String deleteLogs(Integer fileId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "file/log/" + fileId);

        logger.info("Se hace una solicitud al servicio [Gestor de archivos]");

        var requestEntity = new HttpEntity<>(map, headers);
        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.DELETE,
                requestEntity,
                String.class);

        return response.getBody();
    }

    public FilePart getFilePart(Integer fileId, Integer partNumber) {
        RestTemplate restTemplate = new RestTemplate();
        var requestEntity = this.getGenericRequestEntity();
        var builder = this.getBuilder("file/" + fileId + "part/" + partNumber);
        HttpEntity<FilePart> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                FilePart.class);

        return response.getBody();
    }


    private HttpEntity getGenericRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        var requestEntity = new HttpEntity<>(map, headers);
        return requestEntity;
    }

    private UriComponentsBuilder getBuilder(String path) {
        return UriComponentsBuilder.fromHttpUrl(host + path);
    }
}

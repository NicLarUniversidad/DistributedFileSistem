package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.repositories.externals;

import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.entities.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileRepository.class);

    private final RestTemplate restTemplate;

    public UserRepository() {
        this.restTemplate = new RestTemplate();
    }

    public String createIfNotExists(String username, String password) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LinkedMultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.add("Content-disposition", "form-data; username=" + username + "; password=" + password);

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/create-user/");

        logger.info("Se hace una solicitud al servicio [Gestor de archivos] [create-user]");

        var requestEntity = new HttpEntity<>(map, headers);
        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                String.class);

        logger.info("Se devolvió desde el servicio [Gestor de archivos]: " + response.getBody());

        return response.getBody();
    }

    public String login(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LinkedMultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.add("Content-disposition", "form-data; username=" + username + "; password=" + password);

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/login/");

        logger.info("Se hace una solicitud al servicio [Gestor de archivos] [login]");

        var requestEntity = new HttpEntity<>(map, headers);
        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                String.class);

        logger.info("Se devolvió desde el servicio [Gestor de archivos]: " + response.getBody());

        return response.getBody();
    }
}

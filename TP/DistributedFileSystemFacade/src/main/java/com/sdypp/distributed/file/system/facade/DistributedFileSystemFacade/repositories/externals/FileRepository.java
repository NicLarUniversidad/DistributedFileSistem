package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.repositories.externals;

import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.models.FileDetailsModel;
import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.models.FilesDetailModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Component
public class FileRepository {

    public FilesDetailModel getAllFiles(String username) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/files?username="
                + username);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<FilesDetailModel> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                FilesDetailModel.class);
        return response.getBody();
    }
}

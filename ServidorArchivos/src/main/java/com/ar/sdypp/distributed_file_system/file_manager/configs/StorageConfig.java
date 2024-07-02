package com.ar.sdypp.distributed_file_system.file_manager.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class StorageConfig {

    @Bean
    public Storage buildStorage() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("classpath:storage-credentials.json");
        StorageOptions storageOptions = StorageOptions.newBuilder()
                .setProjectId("hybrid-hawk-428007-f9")
                .setCredentials(GoogleCredentials.fromStream(
                        classPathResource.getInputStream()
                )).build();
        Storage storage = storageOptions.getService();
        return storage;
    }
}

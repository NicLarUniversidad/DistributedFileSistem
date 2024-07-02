package com.ar.sdypp.distributed_file_system.file_manager.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
public class StorageConfig {

    @Bean
    public Storage buildStorage() throws IOException {
        StorageOptions storageOptions = StorageOptions.newBuilder()
                .setProjectId("hybrid-hawk-428007-f9")
                .setCredentials(GoogleCredentials.fromStream(new
                        FileInputStream(ResourceUtils.getFile("classpath:storage-credentials.json")))).build();
        Storage storage = storageOptions.getService();
        return storage;
    }
}

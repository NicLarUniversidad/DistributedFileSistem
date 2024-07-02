package com.ar.sdypp.distributed_file_system.file_manager.services;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.common.io.CharSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Service
public class StorageService {

    private static Logger logger = LoggerFactory.getLogger(StorageService.class);

    public static String cred = "";

    public String saveFile(byte[] fileContent, String fileName) throws IOException {
//        Storage storage = StorageOptions.getDefaultInstance().getService();
//        Bucket bucket = storage.create(BucketInfo.of("gs://sdypp-file-system"));
//        Blob blob = bucket.create(fileName, fileContent);
//        return blob.getMediaLink();

        //ClassPathResource classPathResource = new ClassPathResource("classpath:storage-credentials.json");
        StorageOptions storageOptions = StorageOptions.newBuilder()
                .setProjectId("hybrid-hawk-428007-f9")
                .setCredentials(GoogleCredentials.fromStream(
                        //classPathResource.getInputStream()
                        new ByteArrayInputStream(cred.getBytes(StandardCharsets.UTF_8))
                )).build();
        Storage storage = storageOptions.getService();
        long fileSize = fileContent.length;

        Map<String, String> metadata = new HashMap<>();
        metadata.put("filename", fileName);
        metadata.put("content-length", String.valueOf(fileSize));

        BlobInfo blobInfo = BlobInfo
                .newBuilder("sdypp-file-system", fileName)
                .setMetadata(metadata)
                .build();

        final Blob blob = storage.create(blobInfo, fileContent);

        if (blob != null && !blob.getContentType().isBlank()) {
            logger.info("File [{}] uploaded successfully.", fileName);
            return fileName.concat(" uploaded successfully");
        }
        else {
            logger.info("File [{}] could not be uploaded.", fileName);
            return fileName.concat(" failed to upload");
        }


    }
}

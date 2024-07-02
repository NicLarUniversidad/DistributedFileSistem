package com.ar.sdypp.distributed_file_system.file_manager.services;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import com.google.common.io.CharSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Service
public class StorageService {

    private static Logger logger = LoggerFactory.getLogger(StorageService.class);

    @Value("${sdypp.storage.credentials:}")
    public String cred = "";

    public String saveFile(byte[] fileContent, String fileName) throws IOException {
        Storage storage = this.getStorage();
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

    public String getFile(String fileName) throws IOException {
//        FileUrlResource gcsFile = new FileUrlResource("https://storage.cloud.google.com/sdypp-file-system/" + fileName);
//        return StreamUtils.copyToString(
//                gcsFile.getInputStream(),
//                Charset.defaultCharset());
        Storage storage = this.getStorage();
        Blob blob = storage.get("sdypp-file-system", fileName);
        return new String(blob.getContent());
    }


    private Storage getStorage() throws IOException {
        StorageOptions storageOptions = StorageOptions.newBuilder()
                .setProjectId("hybrid-hawk-428007-f9")
                .setCredentials(GoogleCredentials.fromStream(
                        //classPathResource.getInputStream()
                        new ByteArrayInputStream(cred.getBytes(StandardCharsets.UTF_8))
                )).build();
        return storageOptions.getService();
    }
}

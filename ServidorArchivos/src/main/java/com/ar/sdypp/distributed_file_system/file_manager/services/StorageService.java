package com.ar.sdypp.distributed_file_system.file_manager.services;

import com.ar.sdypp.distributed_file_system.file_manager.models.FileDataModel;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class StorageService {

    private static Logger logger = LoggerFactory.getLogger(StorageService.class);

    @Value("${sdypp.storage.credentials}")
    public String cred = "";
    @Value("${sdypp.storage.project}")
    public String projectId = "";
    private String[] bucketNames = {"sdypp-file-system", "sdypp-file-system-replica", "sdypp-file-system-replica-2"};


    public String saveFileOnBuckets(byte[] fileContent, String fileName) throws IOException {
        StringBuilder result = new StringBuilder();
        for (String bucketName : bucketNames) {
            result.append(this.saveFile(fileContent, fileName, bucketName)).append("\n");
        }
        return result.toString();
    }

    public String saveFile(byte[] fileContent, String fileName, String bucketName) throws IOException {
        Storage storage = this.getStorage();
        long fileSize = fileContent.length;
        logger.info("Saving a part with size: " + fileSize);
        Map<String, String> metadata = new HashMap<>();
        metadata.put("filename", fileName);
        metadata.put("content-length", String.valueOf(fileSize));

        BlobInfo blobInfo = BlobInfo
                .newBuilder(bucketName, fileName)
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

    public FileDataModel getFile(String fileName) throws IOException {
        FileDataModel fileData = new FileDataModel();
        var count=1;
        Storage storage = this.getStorage();
        Blob blob = storage.get(bucketNames[0], fileName);
        if (blob != null) {
            fileData.setBucketName(bucketNames[0]);
        }
        while (blob == null && count < bucketNames.length) {
            blob = storage.get(bucketNames[count], fileName);
            if (blob != null) {
                fileData.setBucketName(bucketNames[count]);
            }
            count++;
        }
        fileData.setData(blob.getContent());
        return fileData;
    }


    private Storage getStorage() throws IOException {
        StorageOptions storageOptions = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(
                        //classPathResource.getInputStream()
                        new ByteArrayInputStream(cred.getBytes(StandardCharsets.UTF_8))
                )).build();
        return storageOptions.getService();
    }

    public void update(String fileName, byte[] newContent) throws IOException {
        for (String bucketName : bucketNames) {
            this.update(fileName, newContent, bucketName);
        }
    }

    private void update(String fileName, byte[] newContent, String bucketName) throws IOException {
        Storage storage = this.getStorage();
        Blob blob = storage.get(bucketName, fileName);
        WritableByteChannel channel = blob.writer();
        channel.write(ByteBuffer.wrap(newContent));
        channel.close();
    }
}

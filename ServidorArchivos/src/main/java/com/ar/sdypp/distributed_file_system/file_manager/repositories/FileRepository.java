package com.ar.sdypp.distributed_file_system.file_manager.repositories;

import com.ar.sdypp.distributed_file_system.file_manager.models.FileDetailsModel;
import com.ar.sdypp.distributed_file_system.file_manager.services.StorageService;
import org.jasypt.util.text.StrongTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;

@Component
public class FileRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileRepository.class);

    private final StrongTextEncryptor textEncryptor;
    private final StorageService storageService;
    @Value("${sdypp.encrypt.queue.password}")
    private String passwordEncrypt;

    @Autowired
    public FileRepository(Environment env, StorageService storageService) {
        this.textEncryptor = new StrongTextEncryptor();
        passwordEncrypt = env.getProperty("sdypp.encrypt.queue.password");
        this.textEncryptor.setPassword(passwordEncrypt);
        this.storageService = storageService;
    }

    public FileDetailsModel saveFile(MultipartFile file, String username) throws IOException {
        byte data[] = file.getBytes();
        Path file0 = Paths.get(username + "/" + file.getOriginalFilename()); // username / nombre de archivo

        String fileContain = new String(data, StandardCharsets.UTF_8);
        var encryptData = textEncryptor.encrypt(fileContain).getBytes();
        Files.createDirectories(Paths.get(username));
        Files.write(file0, encryptData);

        var size = file.getSize();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDetailsModel fileEntity = new FileDetailsModel();
        fileEntity.setName(fileName);
        fileEntity.setUsername(username);
        fileEntity.setPath(file0.toString()); // Agrego path, por si en algún momento se cambia de estrategia
        fileEntity.setId(file0.toString());
        fileEntity.setUploadedDate(new Date());
        fileEntity.setSize(size + " bytes");
        return fileEntity;
    }

    public byte[] getFileById(String fileId, String username) throws IOException {
        long startTime = System.currentTimeMillis();
        var fileData = this.storageService.getFile(fileId);
        String plainText = textEncryptor.decrypt(new String(fileData.getData(), "UTF-8"));
        long finishTime = System.currentTimeMillis();
        logger.info("Se recuperó la parte: '{}', desde el bucket: '{}' en [{}] milisegundos", fileId, fileData.getBucketName(), finishTime - startTime);
        return plainText.getBytes();
    }
}

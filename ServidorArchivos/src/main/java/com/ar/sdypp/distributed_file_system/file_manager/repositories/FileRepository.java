package com.ar.sdypp.distributed_file_system.file_manager.repositories;

import com.ar.sdypp.distributed_file_system.file_manager.services.CipherService;
import com.ar.sdypp.distributed_file_system.file_manager.services.StorageService;
import org.jasypt.util.text.StrongTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import static org.springframework.cache.interceptor.SimpleKeyGenerator.generateKey;

@Component
public class FileRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileRepository.class);

    private final StorageService storageService;
    private final CipherService cipherService;

    @Autowired
    public FileRepository(StorageService storageService, CipherService cipherService) throws Exception {
        this.cipherService = cipherService;
        this.storageService = storageService;
    }

    public byte[] getFileById(String fileId, String username) throws Exception {
        long startTime = System.currentTimeMillis();
        var fileData = this.storageService.getFile(fileId);
        logger.info(new String(fileData.getData()));
        String plainText = new String(cipherService.decrypt(fileData.getData()));
        logger.info(plainText);
        long finishTime = System.currentTimeMillis();
        logger.info("Se recuperó la parte: '{}', desde el bucket: '{}' en [{}] milisegundos", fileId, fileData.getBucketName(), finishTime - startTime);
        return plainText.getBytes();
    }
}

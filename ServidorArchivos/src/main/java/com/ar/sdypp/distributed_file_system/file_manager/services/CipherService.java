package com.ar.sdypp.distributed_file_system.file_manager.services;

import org.jasypt.util.text.StrongTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.spec.KeySpec;
import java.util.Base64;

@Service
public class CipherService {

    @Value("${sdypp.encrypt.queue.password}")
    private String passwordEncrypt;
    @Value("${sdypp.encrypt.queue.algorithm}")
    private String algorithm;
    private Key key;
    private Cipher cipherEncrypt;
    private Cipher cipherDecrypt;


    @Autowired
    public CipherService(Environment env, StorageService storageService) throws Exception {
        passwordEncrypt = env.getProperty("sdypp.encrypt.queue.password");
        this.algorithm = env.getProperty("sdypp.encrypt.queue.algorithm");
        Key key = this.generateKey();
        cipherEncrypt = Cipher.getInstance(algorithm);
        cipherEncrypt.init(Cipher.ENCRYPT_MODE, key);
        cipherDecrypt = Cipher.getInstance(algorithm);
        cipherDecrypt.init(Cipher.DECRYPT_MODE, key);
    }

    private Key generateKey() throws Exception {
        if (key == null)
            key = new SecretKeySpec(passwordEncrypt.getBytes(StandardCharsets.UTF_8), algorithm);
        return key;
    }

    public byte[] encrypt(byte[] data) throws Exception {
        byte[] encVal = cipherEncrypt.doFinal(data);
        return encVal;
    }

    public byte[] decrypt(byte[] data) throws Exception {
        byte[] plainData = cipherDecrypt.doFinal(data);
        return plainData;
    }
}

package com.ar.sdypp.distributed_file_system.file_manager.repositories;

import com.ar.sdypp.distributed_file_system.file_manager.models.FileDetailsModel;
import org.jasypt.util.text.StrongTextEncryptor;
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
import java.util.Date;

@Component
public class FileRepository {

    private final StrongTextEncryptor textEncryptor;

    public FileRepository() {
        this.textEncryptor = new StrongTextEncryptor();
        this.textEncryptor.setPassword("ultrasecreta");
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

    public FileDetailsModel saveStringAsFile(MultipartFile file, String username) throws IOException {
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

    public String getFileById(String fileId, String username) throws IOException {
        // Por ahora el ID podría ser el path
        String path = username + "/" + fileId;
        File file = new File(path);
        if (file.exists()) {
            //var content = StreamUtils.copyToString( new ClassPathResource(path).getInputStream(), Charset.defaultCharset()  );

            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line = null;
            do {
                line = reader.readLine();
                if (line != null) {
                    content.append(line);
                }
            } while(line != null);
            reader.close();
            String plainText = textEncryptor.decrypt(content.toString());
            return plainText;
        }
        else {
            throw new FileNotFoundException(fileId);
        }
    }
}

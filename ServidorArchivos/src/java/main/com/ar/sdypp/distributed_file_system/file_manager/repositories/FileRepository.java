package com.ar.sdypp.distributed_file_system.file_manager.repositories;

import com.ar.sdypp.distributed_file_system.file_manager.models.FileDetailsModel;
import com.ar.sdypp.distributed_file_system.file_manager.services.LoadBalancerService;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class FileRepository {

    @Autowired
    private LoadBalancerService loadBalancerService;

    public FileDetailsModel save(MultipartFile file, String username) throws IOException {

        byte data[] = file.getBytes();
        Path file0 = Paths.get(username + "/");
        Files.write(file0, data);

        String size = calculateSize(file.getSize());
        var response = new FileDetailsModel("prueba", username, file.getName(), new Date(), size);
        return response;
    }
    private File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
        multipart.transferTo(convFile);
        return convFile;
    }

    private String calculateSize(long size) {
        String response = "";
        response = size + " bytes";
        return response;
    }
}
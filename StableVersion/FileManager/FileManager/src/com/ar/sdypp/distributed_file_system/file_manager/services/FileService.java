package com.ar.sdypp.distributed_file_system.file_manager.services;


import com.ar.sdypp.distributed_file_system.file_manager.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public void save(MultipartFile file, String fileName) throws IOException, URISyntaxException {
        this.fileRepository.save(file, fileName);
    }
}
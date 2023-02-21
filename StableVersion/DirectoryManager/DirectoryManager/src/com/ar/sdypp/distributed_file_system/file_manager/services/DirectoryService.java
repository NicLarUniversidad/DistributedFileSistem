package com.ar.sdypp.distributed_file_system.file_manager.services;


import com.ar.sdypp.distributed_file_system.file_manager.repositories.DirectoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryAlreadyExistsException;

@Service
public class DirectoryService {

    @Autowired
    private DirectoryRepository directoryRepository;

    public void save(String directoryName) throws IOException, URISyntaxException {
        this.directoryRepository.save(directoryName);
    }
}
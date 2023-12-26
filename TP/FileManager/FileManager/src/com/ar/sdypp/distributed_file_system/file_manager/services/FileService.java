package com.ar.sdypp.distributed_file_system.file_manager.services;


import com.ar.sdypp.distributed_file_system.file_manager.entities.FileDetailsEntity;
import com.ar.sdypp.distributed_file_system.file_manager.models.FileDetailsModel;
import com.ar.sdypp.distributed_file_system.file_manager.models.FilesDetailsModel;
import com.ar.sdypp.distributed_file_system.file_manager.repositories.FileDetailsRepository;
import com.ar.sdypp.distributed_file_system.file_manager.repositories.FileRepository;
import com.ar.sdypp.distributed_file_system.file_manager.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileDetailsRepository fileDetailsRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;

    public void save(MultipartFile file, String fileName) throws IOException, URISyntaxException {
        this.fileRepository.save(file, fileName);
    }

    public FilesDetailsModel getUserFiles(String username) {
        var files = this.fileDetailsRepository.findAllByUsername(username);
        var response = new FilesDetailsModel();
        files.forEach(f ->
                response.getFiles().add(modelMapperUtil.map(f, FileDetailsModel.class))
        );
        return response;
    }

}
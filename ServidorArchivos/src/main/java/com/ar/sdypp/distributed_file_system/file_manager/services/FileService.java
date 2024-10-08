package com.ar.sdypp.distributed_file_system.file_manager.services;


import com.ar.sdypp.distributed_file_system.file_manager.models.FileDetailsModel;
import com.ar.sdypp.distributed_file_system.file_manager.repositories.FileRepository;
import com.ar.sdypp.distributed_file_system.file_manager.utils.ModelMapperUtil;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    private FileRepository fileRepository;
    private ModelMapperUtil modelMapperUtil;

    @Autowired
    public FileService(FileRepository fileRepository, ModelMapperUtil modelMapperUtil) {
        this.fileRepository = fileRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    //Permite almacenar archivos en la base de datos
    public FileDetailsModel save(MultipartFile file, String username) throws IOException {
        logger.info("Recibido: {}, username: {}", file.getOriginalFilename(), username); ;
        return this.fileRepository.saveFile(file, username);
    }

    public byte[] getFileById(String fileId, String username) throws IOException {
        return this.fileRepository.getFileById(fileId, username);
    }
}
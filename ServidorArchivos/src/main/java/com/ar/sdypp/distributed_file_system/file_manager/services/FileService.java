package com.ar.sdypp.distributed_file_system.file_manager.services;


import com.ar.sdypp.distributed_file_system.file_manager.models.FileDetailsModel;
import com.ar.sdypp.distributed_file_system.file_manager.repositories.FileReaderRepository;
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
//    @Autowired
//    private FileDetailsRepository fileDetailsRepository;
    private ModelMapperUtil modelMapperUtil;
    private final FileReaderRepository fileReaderRepository;

    @Autowired
    public FileService(FileRepository fileRepository, ModelMapperUtil modelMapperUtil, FileReaderRepository fileReaderRepository) {
        this.fileRepository = fileRepository;
        this.modelMapperUtil = modelMapperUtil;
        this.fileReaderRepository = fileReaderRepository;
    }

    //Permite almacenar archivos en la base de datos
    public FileDetailsModel save(MultipartFile file, String username) throws IOException {
        logger.info("Recibido: {}, username: {}", file.getOriginalFilename(), username); ;
        return this.fileRepository.saveFile(file, username);
    }


//    //Permite descargar archivos de la base de datos
//    public FileSystemResource getFile(String id) throws FileNotFoundException {
//        return this.getFile(id);
//    }


//    public FilesDetailsModel getUserFiles(String username) {
////        var files = this.fileDetailsRepository.findAllByUsername(username);
////        var response = new FilesDetailsModel();
////        files.forEach(f ->
////                response.getFiles().add(modelMapperUtil.map(f, FileDetailsModel.class))
////        );
////        return response;
//        return null;
//    }

    public byte[] getFileById(String fileId, String username) throws IOException {
        return this.fileRepository.getFileById(fileId, username);
    }
}
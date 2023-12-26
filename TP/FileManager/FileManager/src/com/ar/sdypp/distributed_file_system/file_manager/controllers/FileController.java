package com.ar.sdypp.distributed_file_system.file_manager.controllers;


import com.ar.sdypp.distributed_file_system.file_manager.models.FilesDetailsModel;
import com.ar.sdypp.distributed_file_system.file_manager.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file")
    public String saveFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String fileName)
            throws IOException, URISyntaxException {
        logger.info("Recibido: file:{}, fileName: {}", file.getName(), fileName);
        fileService.save(file, fileName);
        return "OK";
    }

    @GetMapping("/files")
    public FilesDetailsModel getUserFiles(@RequestParam(name = "username") String username) throws IOException, URISyntaxException {
        logger.info("Recibido: username: {}", username);
        var files = this.fileService.getUserFiles(username);
        return files;
    }



    @ExceptionHandler(IOException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Archivo ya existe")
    public String ioExceptionHandler() {

        return "Ocurrió un error al escribir el archivo";
    }
}

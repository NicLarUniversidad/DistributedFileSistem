package com.ar.sdypp.distributed_file_system.file_manager.controllers;


import com.ar.sdypp.distributed_file_system.file_manager.models.FileDetailsModel;
import com.ar.sdypp.distributed_file_system.file_manager.services.FileService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // Recuperar parte archivo
    @GetMapping("/file")
    public ResponseEntity<byte[]> getFile(@PathParam("id") String id, @PathParam("username") String username) throws Exception {
        var fileString = fileService.getFileById(id, username);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment : filename\"" + id +"\"")
                .body(fileString);
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Archivo ya existe")
    public String ioExceptionHandler() {

        return "Ocurrió un error al escribir el archivo";
    }
    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Archivo ya existe")
    public String fileNotFoundExceptionHandler() {

        return "No se ha encontrado el archivo";
    }
}

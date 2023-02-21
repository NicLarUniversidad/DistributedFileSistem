package com.ar.sdypp.distributed_file_system.file_manager.controllers;


import com.ar.sdypp.distributed_file_system.file_manager.services.DirectoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class DirectoryController {

    private static final Logger logger = LoggerFactory.getLogger(DirectoryController.class);

    private final DirectoryService directoryService;

    @Autowired
    public DirectoryController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @PostMapping("/directory")
    public String saveDirectory(@RequestParam("name") String directoryName,
                           HttpServletResponse response) throws IOException, URISyntaxException {
        logger.info("Recibido: directoryName: {}", directoryName);
        directoryService.save(directoryName);
        return "OK";
    }


    @ExceptionHandler(IOException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "El directorio ya existe")
    public String ioExceptionHandler() {
        return "Ocurrió un error al crear el directorio";
    }
}

package com.ar.sdypp.distributed_file_system.file_manager.controllers;


import com.ar.sdypp.distributed_file_system.file_manager.models.FileDetailsModel;
import com.ar.sdypp.distributed_file_system.file_manager.models.FilesDetailsModel;
import com.ar.sdypp.distributed_file_system.file_manager.services.FileService;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/file")
    public FileDetailsModel saveFile(@RequestParam("file") MultipartFile file, @RequestParam("user") String user)
            throws IOException, URISyntaxException {
        logger.info("Recibido: file:{}, username: {}", file.getName(), user);
        FileDetailsModel response = fileService.save(file, user);
        return response;
    }

    // Descargar archivo
    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable id id) throws FileNotFoundException{
        FileDetailsEntity fileEntity = fileService.getFileById(id).get();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment : filename\"" + fileEntity.getName()+"\"")
                .body(fileEntity.getData());
    }



    @GetMapping("/files")
    public FilesDetailsModel getUserFiles(@RequestParam(name = "username") String username) throws IOException, URISyntaxException {
        logger.info("Recibido: username: {}", username);
        var files = this.fileService.getUserFiles(username);
        return files;
    }

    @GetMapping("/get-file/{file-id}")
    public FileSystemResource getFileById(@PathVariable(name = "file-id") String fileId) throws IOException, URISyntaxException {
        logger.info("Recibido: file id: {}", fileId);
        var file = this.fileService.getFileById(fileId);
        return file;
    }


    //Trae la lista de archivos
    @GetMapping("/allfiles")
    public ResponseEntity<List<ResponseFile>> getListFiles(){
        List<ResponseFile> files = fileService.getAllFiles();
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }


    @ExceptionHandler(IOException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Archivo ya existe")
    public String ioExceptionHandler() {

        return "Ocurrió un error al escribir el archivo";
    }
}

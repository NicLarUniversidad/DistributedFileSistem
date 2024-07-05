package com.sdypp.distributed.file.system.facade.controllers;

import com.sdypp.distributed.file.system.facade.models.FileDetailsModel;
import com.sdypp.distributed.file.system.facade.models.FileModel;
import com.sdypp.distributed.file.system.facade.models.FilesDetailModel;
import com.sdypp.distributed.file.system.facade.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Random;

//Acá se definen los recursos o las "URL"
@RestController//Con esto decís que es REST
@CrossOrigin
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

//    @PostMapping("/upload-file")
//    //@RequestBody es para optener datos del cuerpo del request, siempre es un objeto o si no te toma el
//    //valor "plano" del cuerpo, por ejemplo un String.
//    public void login(@RequestBody FileModel loginModel) {
//
//    }

    @Cacheable("health")
    @GetMapping("/health")
    public String health() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Se ejecutó el método health");
        return "OK";
    }

    @GetMapping("/myAccount")
    public String myAccount() {
        return "OK";
    }

    @GetMapping("/files")
    public FilesDetailModel myFiles() {
        return this.fileService.getAllFiles();
    }

    @PostMapping("/upload-file")
    public FileDetailsModel uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return this.fileService.uploadFile(file);
    }

    @GetMapping("/get-file/{file-id}")
    @Cacheable(value = "get-file")
    public byte[] getFile(@PathVariable("file-id") String fileId) throws IOException {
        System.out.println("Recibido id archivo = " + fileId);
        return this.fileService.getFile(fileId);
    }
}

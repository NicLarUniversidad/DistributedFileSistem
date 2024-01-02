package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.controllers;

import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.models.FileDetailsModel;
import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.models.FileModel;
import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.models.FilesDetailModel;
import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("/health")
    public String health() {
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
}

package com.sdypp.distributed.file.system.facade.controllers;

import com.sdypp.distributed.file.system.facade.models.*;
import com.sdypp.distributed.file.system.facade.services.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public FileModel uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return this.fileService.uploadFile(file);
    }

    @PostMapping("/update-file/{file-id}")
    @CacheEvict("get-file")
    public FileModel updateFile(@RequestParam("newText") String newText, @PathVariable("file-id") String fileId) throws IOException {
        return this.fileService.updateFile(newText, fileId);
    }

    @PostMapping("/cache/clean/{file-id}")
    @CacheEvict("get-file")
    public String cleanFileCache(@PathVariable("file-id") String fileId) {
        return "OK";
    }

    @GetMapping("/get-file/{file-id}")
    @Cacheable("get-file")
    public String getFile(@PathVariable("file-id") String fileId) throws IOException {
        System.out.println("Recibido id archivo = " + fileId);
        FileDownloadModel model = new FileDownloadModel();
        model.setResource(this.fileService.getFile(fileId));

        return new String(model.getResource().getContentAsByteArray());
    }

    @DeleteMapping("/delete-file/{file-id}")
    public String deleteFile(@PathVariable("file-id") Integer fileId, HttpServletResponse response) {
        return fileService.deleteFile(fileId);
    }

    @GetMapping("/file/parts/{file-id}")
    public PartModels getFileParts(@PathVariable("file-id") Integer fileId) throws IOException {
        System.out.println("Recibido id archivo = " + fileId);
        return this.fileService.getFileParts(fileId);
    }

    @GetMapping("/file/log/{file-id}")
    public FileLogsModel getFileLogs(@PathVariable("file-id") Integer fileId) throws IOException {
        System.out.println("Recibido id archivo = " + fileId);
        return this.fileService.getFileLogs(fileId);
    }

    @DeleteMapping("/file/log/{file-id}")
    public String deleteLogs(@PathVariable("file-id") Integer fileId) throws IOException {
        System.out.println("Recibido id archivo = " + fileId);
        return this.fileService.deleteLogs(fileId);
    }

    @GetMapping("/file/data/{file-id}")
    @Cacheable("file-data")
    public FileModel getFileData(@PathVariable("file-id") Integer fileId) {
        System.out.println("Recibido id archivo = " + fileId);
        return this.fileService.getFileData(fileId);
    }

    @PostMapping("/file/lock/{file-id}")
    public FileModel lockFile(@PathVariable("file-id") Integer fileId) throws IOException {
        System.out.println("Recibido id archivo = " + fileId);
        return this.fileService.lockFile(fileId);
    }
}

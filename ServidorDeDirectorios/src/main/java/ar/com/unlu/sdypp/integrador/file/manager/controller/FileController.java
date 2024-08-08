package ar.com.unlu.sdypp.integrador.file.manager.controller;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.FileCrud;
import ar.com.unlu.sdypp.integrador.file.manager.models.FileListModel;
import ar.com.unlu.sdypp.integrador.file.manager.models.FileLogsModel;
import ar.com.unlu.sdypp.integrador.file.manager.models.PartsModel;
import ar.com.unlu.sdypp.integrador.file.manager.services.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PutMapping("/file")
    public String saveFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String fileName) throws Exception {
        fileService.save(file, fileName, null, false);
        return "OK";
    }

    @GetMapping("/get-file/{fileId}")
    public Resource getFile(@PathVariable("fileId") Integer fileId) throws IOException, InterruptedException {
        return this.fileService.getFile(fileId);
    }

    @PostMapping("/upload-file")
    public FileCrud uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("username") String username, @RequestParam("x-chunk") Integer id,
                               @RequestParam("chunk-append") Boolean append) throws Exception {
        logger.info(String.format("Recibido: %s", file.getOriginalFilename()));
        return fileService.uploadFile(file, username, id, append);
    }

    @PostMapping("/update-file/{file-id}")
    public FileCrud updateFile(@RequestParam("file") MultipartFile file, @RequestParam("username") String username, @PathVariable("file-id") Integer fileId) throws Exception {
        logger.info(String.format("Recibido: %s", file.getOriginalFilename()));
        return fileService.updateFile(fileId, file, username);
    }

    @GetMapping("/files/{username}")
    public FileListModel getFile(@PathVariable("username") String username, Pageable pageable) {
        return fileService.getAllFiles(username, pageable);
    }

    @DeleteMapping("/delete-file/{file-id}")
    public String deleteFile(@PathVariable("file-id") Integer fileId, HttpServletResponse response) {
        fileService.deleteFile(fileId);
        return "OK";
    }

    @GetMapping("/file/parts/{file-id}")
    public PartsModel getFile(@PathVariable("file-id") Integer fileId, HttpServletResponse response) {
        return fileService.getParts(fileId);
    }

    @GetMapping("/file/log/{fileId}")
    public FileLogsModel getFileLogs(@PathVariable("fileId") Integer fileId) throws Exception {
        return this.fileService.getFileLogs(fileId);
    }

    @PostMapping("/file/lock/{fileId}")
    public FileCrud lockFile(@PathVariable("fileId") Integer fileId) throws Exception {
        return this.fileService.lockFile(fileId);
    }

    @GetMapping("/file/data/{file-id}")
    public FileCrud getFileData(@PathVariable("file-id") Integer fileId) {
        System.out.println("Recibido id archivo = " + fileId);
        return this.fileService.getFileData(fileId);
    }

    @DeleteMapping("/file/log/{fileId}")
    public String deleteLogs(@PathVariable("fileId") Integer fileId) throws Exception {
        this.fileService.deleteLogs(fileId);
        return "OK";
    }
}

package ar.com.unlu.sdypp.integrador.file.manager.controller;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.FileCrud;
import ar.com.unlu.sdypp.integrador.file.manager.cruds.FilePartCrud;
import ar.com.unlu.sdypp.integrador.file.manager.models.FileListModel;
import ar.com.unlu.sdypp.integrador.file.manager.models.FileModel;
import ar.com.unlu.sdypp.integrador.file.manager.models.PartsModel;
import ar.com.unlu.sdypp.integrador.file.manager.servers.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @PutMapping("/file")
    public String saveFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String fileName, HttpServletResponse response) throws IOException {
        fileService.save(file, fileName);
        return "OK";
    }

    @GetMapping("/get-file/{fileId}")
    public Resource getFile(@PathVariable("fileId") Integer fileId) throws IOException {
        return this.fileService.getFile(fileId);
    }

    @PostMapping("/upload-file")
    public FileCrud uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) throws IOException {
        logger.info(String.format("Recibido: %s", file.getOriginalFilename()));
        return fileService.uploadFile(file, username);
    }

    @GetMapping("/files/{username}")
    public FileListModel getFile(@PathVariable("username") String username, HttpServletResponse response) {
        return fileService.getAllFiles(username);
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
/*

    @PostMapping("/file")
    public FileModel updateFile(@RequestParam("file") MultipartFile file, @RequestBody RealFileLocation realFileLocation, HttpServletResponse response) throws IOException {
        try {
            return fileService.updateFile(file, realFileLocation);
        } catch (IOException e) {
            String message = String.format("No se encontró el archivo: [%s]", realFileLocation.getName());
            logger.error(message);
            response.sendError(404, message);
        }
        return null;
    }

    @DeleteMapping("/file")
    public String deleteFile(@RequestBody RealFileLocation realFileLocation, HttpServletResponse response) throws IOException {
        try {
            this.fileService.delete(realFileLocation);
            return "OK";
        } catch (IOException e) {
            String message = String.format("No se encontró el archivo: [%s]", realFileLocation.getName());
            logger.error(message);
            response.sendError(404, message);
        }
        return "No borrado";
    }*/
}

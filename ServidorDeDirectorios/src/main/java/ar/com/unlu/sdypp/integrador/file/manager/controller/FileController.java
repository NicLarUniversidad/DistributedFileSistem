package ar.com.unlu.sdypp.integrador.file.manager.controller;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.FileCrud;
import ar.com.unlu.sdypp.integrador.file.manager.models.FileListModel;
import ar.com.unlu.sdypp.integrador.file.manager.models.FileLogsModel;
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

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @PutMapping("/file")
    public String saveFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String fileName, HttpServletResponse response) throws Exception {
        fileService.save(file, fileName, null);
        return "OK";
    }

    @GetMapping("/get-file/{fileId}")
    public Resource getFile(@PathVariable("fileId") Integer fileId) throws IOException, InterruptedException {
        return this.fileService.getFile(fileId);
    }

    @PostMapping("/upload-file")
    public FileCrud uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("username") String username, @RequestParam("file-id") Integer id) throws Exception {
        logger.info(String.format("Recibido: %s", file.getOriginalFilename()));
        return fileService.uploadFile(file, username, id);
    }

    @PostMapping("/update-file/{file-id}")
    public FileCrud updateFile(@RequestParam("file") MultipartFile file, @RequestParam("username") String username, @PathVariable("file-id") Integer fileId) throws Exception {
        logger.info(String.format("Recibido: %s", file.getOriginalFilename()));
        return fileService.updateFile(fileId, file, username);
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

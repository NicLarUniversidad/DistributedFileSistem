package ar.com.unlu.sdypp.integrador.file.manager.controller;

import ar.com.unlu.sdypp.integrador.file.manager.models.FileModel;
import ar.com.unlu.sdypp.integrador.file.manager.servers.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @PutMapping("/file")
    public String saveFile(@RequestParam("file") MultipartFile file, @RequestBody String fileName, HttpServletResponse response) {
        fileService.save(file, fileName);
        return "OK";
    }
/*
    @GetMapping("/file")
    public FileModel getFile(@RequestBody String path, HttpServletResponse response) {
        try {
            return fileService.get(path);
        } catch (IOException e) {
            logger.error("Se produzco un error al intentar recuperar el archivo, path: [{}]", path, e);
            response.setStatus(204);
        }
        return null;
    }

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

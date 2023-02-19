package ar.com.unlu.sdypp.integrador.file.server.controllers;

import ar.com.unlu.sdypp.integrador.file.server.modelsDirectoryModel;
import ar.com.unlu.sdypp.integrador.file.server.models.RealDirectoryLocation;
import ar.com.unlu.sdypp.integrador.file.server.services.DirectoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.DirectoryAlreadyExistsException;

@RestController
public class DirectoryController {

    private static final Logger logger = LoggerFactory.getLogger(DirectoryController.class);

    @Autowired
    private DirectoryService directoryService;

    @PostMapping("/directory")
    public String saveDirectory(@RequestParam("name") String name,
                           @RequestParam("path") String path,
                           HttpServletResponse response) {
        RealDirectoryLocation ubicacionRealDeDirectorio = new RealDirectoryLocation(name, path);
        try {
            directoryService.save(path, ubicacionRealDeDirectorio);
            return "OK";
        } catch (DirectoryAlreadyExistsException e) {
            logger.error("Este directorio ya existe, [ruta:] [{}]",
                    ubicacionRealDeDirectorio.getPath(),
                    ubicacionRealDeDirectorio.getName(),
                    e);
            response.setStatus(400);
            return e.getLocalizedMessage();
        }
    }

    @GetMapping("/directory")
    public DirectoryModel getDirectory(@RequestBody String path, HttpServletResponse response) {
        try {
            return directoryService.get(path);
        } catch (IOException e) {
            logger.error("Se ha producido un error al intentar recuperar la ruta: [{}]", path, e);
            response.setStatus(204);
        }
        return null;
    }

    @DeleteMapping("/directory")
    public String deleteFile(@RequestBody RealDirectoryLocation realDirectoryLocation, HttpServletResponse response) throws IOException {
        try {
            this.directoryService.delete(realFileLocation);
            return "OK";
        } catch (IOException e) {
            String message = String.format("No se encontró la ruta: [%s]", realDirectoryLocation.getName());
            logger.error(message);
            response.sendError(404, message);
        }
        return "ERROR: No se puedo eliminar el directorio";
    }
}

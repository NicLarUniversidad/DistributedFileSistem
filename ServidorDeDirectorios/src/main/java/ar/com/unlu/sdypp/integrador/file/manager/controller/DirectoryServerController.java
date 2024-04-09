package ar.com.unlu.sdypp.integrador.file.manager.controller;

import ar.com.unlu.sdypp.integrador.file.manager.models.DirectoryServerModel;
import ar.com.unlu.sdypp.integrador.file.manager.servers.DirectoryServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@RestController
public class DirectoryServerController {

    private static final Logger logger = LoggerFactory.getLogger(DirectoryServerController.class);

    @Autowired
    private DirectoryServerService directoryServerService;
/*
    @PutMapping("/file")
    public String saveRoute(@RequestParam("route") MultipartFile file, @RequestBody String routeName, HttpServletResponse response) {
        directoryServerService.saveFile(routeName, file);
        logger.info ("Procesando operación...");
        return "OK";
    }
*/
    @PutMapping("/path")
    public String saveRoute(@RequestBody DirectoryServerModel directoryModel) {
        directoryServerService.save(directoryModel);
        logger.info ("Procesando operación...");
        return "OK";
    }
    /*
    @GetMapping("/getFile")
    public List<listRoute> getListOfData(@RequestBody List<Integer> fileid) {
        List<listRoute> findAllByFileId = fileService.getListOfData(fileid);
        try {
            return findAllByFileId;
        } catch (IOException e) {
            logger.error("Se produzco un error al intentar recuperar la ruta, path: [{}]", path, e);
            response.setStatus(204);
        }
        return null;
    }*/

    @GetMapping("/directory/list")
    public Iterable<DirectoryServerModel> getAll() {
        return this.directoryServerService.getAll();
    }
}
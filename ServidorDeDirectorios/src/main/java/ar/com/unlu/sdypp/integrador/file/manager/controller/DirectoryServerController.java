package ar.com.unlu.sdypp.integrador.file.manager.controller;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.ServerDirectory;
import ar.com.unlu.sdypp.integrador.file.manager.services.DirectoryServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String saveRoute(@RequestBody ServerDirectory directoryModel) {
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
    public Iterable<ServerDirectory> getAll() {
        return this.directoryServerService.getAll();
    }
}
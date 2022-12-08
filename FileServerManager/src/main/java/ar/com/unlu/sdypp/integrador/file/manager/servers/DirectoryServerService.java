package ar.com.unlu.sdypp.integrador.file.manager.servers;

import ar.com.unlu.sdypp.integrador.file.manager.repositories.DirectoryServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@Service
public class DirectoryServerService {

    private final FileServerRepository fileServerRepository;

    @Autowired
    private DirectoryServerRepository directoryServerRepository;
    private DirectoryServerModel directoryServerModel;
    private ListRoute list;

    @Autowired /*Crear nueva ruta*/
    public void setRoute (int id,String route) {
        this.directoryServerRepository.save(list);

    }

    @Autowired /*Eliminar ruta*/
    public void deleteRoute (int id) {
        this.directoryServerRepository.delete(id);
    }

    @Autowired /*Motrar rutas*/
    public List<listRoute> showRoutes(ArrayList<Integer> fileid){
        List<listRoute> findAllByFileId = repository.findAllByFileId(fileid);
        return findAllByFileId;
    }

    /*
    @Autowired /*Guardar archivo*/
    public String saveFile (String nameRoute, File objFile){
        /**/
        return "OK";
    }*/


   /* @Override /*Cambiar rutas*/
    /*public void updateRoute () {

    }*/



}

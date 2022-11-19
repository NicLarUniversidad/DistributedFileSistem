package ar.com.unlu.sdypp.integrador.file.manager.servers;

import ar.com.unlu.sdypp.integrador.file.manager.repositories.DirectoryServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@Service
public class DirectoryServerService {

    @Autowired
    private DirectoryServerRepository directoryServerRepository;
    private DirectoryServerModel directoryServerModel;

    @Override /*Crear nueva ruta*/
    public void setRoute (int id,String route) {
        this.directoryServerRepository.save(id, route);

    }

    @Override /*Eliminar ruta*/
    public void deleteRoute (int id) {
        this.directoryServerRepository.delete(id);
    }

    @Override /*Motrar rutas*/
    public List<listRoute> showRoutes () {

    }

   /* @Override /*Cambiar rutas*/
    /*public void updateRoute () {

    }*/



}

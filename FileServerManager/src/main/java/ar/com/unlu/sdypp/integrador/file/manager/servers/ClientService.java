package ar.com.unlu.sdypp.integrador.file.manager.servers;

import ar.com.unlu.sdypp.integrador.file.manager.repositories.DirectoryServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@Service
public class ClientService {

    private final FileServerRepository fileServerRepository;

    @Autowired /*Agregar cliente*/
    public void setClient (int id, String name, String job_title,boolean cambiar_permiso, int permiso) {

    }

    @Autowired /*Cambiar permisos al cliente*/
    public void setPermisos (int id,int permiso) {

    }

    @Autowired /*Crear archivo*/
    public void createFile (int name) {

    }

    @Autowired /*Borrar archivo*/
    public void deleteFile (int name) {

    }

    @Autowired /*Crear ruta*/
    public void createRoute (int name) {

    }

    @Autowired /*Borrar ruta*/
    public void deleteRoute (int name) {

    }


    @Autowired /*Leer archivo*/

    @Autowired /*Editar archivo*/


}

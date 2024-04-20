package ar.com.unlu.sdypp.integrador.file.manager.servers;

import ar.com.unlu.sdypp.integrador.file.manager.repositories.DirectoryServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ar.com.unlu.sdypp.integrador.file.manager.models.ClientModels

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public List<ClientModels> listAll(){
        return clientRepository.findAll();
    }

    public ClientModels listById(int id){
        return clientRepository.ListById(id).get();
    }

    public ClientModels create(ClientModels client){
        return clientRepository.save(client);
    }

    public ClientModels update(ClientModels client){
        return clientRepository.save(client);
    }

    public void deleteById(int id){
        clientRepository.deleteById(id);
    }

    /*
    public void setClient (int id, String name, String job_title,boolean cambiar_permiso, int permiso) {

    }

    public void setPermisos (int id,int permiso) {

    }

    public void createFile (string name) {

    }

    public void deleteFile (string name) {

    }

    public void createRoute (string name) {

    }

    public void deleteRoute (string name) {

    }


    */
}
package ar.com.unlu.sdypp.integrador.file.manager.servers;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.User;
import ar.com.unlu.sdypp.integrador.file.manager.exceptions.UserAlreadyExistsException;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.ClientRepository;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.DirectoryServerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ar.com.unlu.sdypp.integrador.file.manager.models.ClientModels;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    private final ModelMapper modelMapper;

    public ClientService() {
        this.modelMapper = new ModelMapper();
    }

    public List<ClientModels> listAll(){
        var clients = clientRepository.findAll();
        List<ClientModels> clientsDTO = (List<ClientModels>)modelMapper.map(clients, List.class);
        return clientsDTO;
    }

    public ClientModels listById(int id){
        var client = clientRepository.findById(id).get();
        return modelMapper.map(client, ClientModels.class);
    }

    public ClientModels create(ClientModels client) throws UserAlreadyExistsException {
        var clientCrud = modelMapper.map(client, User.class);

        var clients = this.clientRepository.findByUsername(client.getUsername());
        if (clients.size() != 0) {
            throw new UserAlreadyExistsException("Ya existe el usuario con username=\"" + client.getUsername() + "\"");
        }

        clientCrud = clientRepository.save(clientCrud);
        return modelMapper.map(clientCrud, ClientModels.class);
    }

    public ClientModels update(ClientModels client){
        var clientCrud = modelMapper.map(client, User.class);
        clientCrud = clientRepository.save(clientCrud);
        return modelMapper.map(clientCrud, ClientModels.class);
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
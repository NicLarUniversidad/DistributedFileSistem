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

@RestController
@RequestMapping("/client")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(DirectoryServerController.class);

    private ClientService clientService;

    @GetMapping
    private ResponseEntity<?> listAll (){
        List<ClientModels> clients = clientService.listAll();
        return ResposeEntity.ok(clients);
    }

    @GetMappping("/{id}")
    private ResponseEntity<?> ListById(@PathVariable int id){
        ClientModels client = clientService.lisyById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping
    private ResponseEntity<?> create(@RequestBody ClientModels client){
        ClientModels clientCreate = clientService.create(client);
        return ResponseEntity.ok(clientCreate);
    }


    @PutMapping("/id")
    private ResponseEntity<?> update(@PathVariable int id, @RequestBody ClientModels client){
        client.setId(id);
        ClientModels clientUpdate = clientService.update(client);
        return ResponseEntity.ok(clientUpdate);
    }

/*
    @DeleteMapping("/id")
    private ResponseEntity<?> deleteById(@PathVariable int id){
        clientService.deleteById(id);
        return ResponseEntity.ok(null);
    }*/

}
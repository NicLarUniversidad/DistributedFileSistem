package ar.com.unlu.sdypp.integrador.file.manager.controller;

import ar.com.unlu.sdypp.integrador.file.manager.exceptions.UserAlreadyExistsException;
import ar.com.unlu.sdypp.integrador.file.manager.models.ClientModels;
import ar.com.unlu.sdypp.integrador.file.manager.models.DirectoryServerModel;
import ar.com.unlu.sdypp.integrador.file.manager.servers.ClientService;
import ar.com.unlu.sdypp.integrador.file.manager.servers.DirectoryServerService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(DirectoryServerController.class);

    @Autowired
    private ClientService clientService;

    @GetMapping
    private ResponseEntity<?> listAll (){
        List<ClientModels> clients = clientService.listAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> ListById(@PathVariable int id){
        System.out.printf("id = " + String.valueOf(id));
        ClientModels client = clientService.listById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping
    private ResponseEntity<?> create(@RequestBody ClientModels client) throws UserAlreadyExistsException {
        ClientModels clientCreate = clientService.create(client);
        return ResponseEntity.ok(clientCreate);
    }


    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable int id, @RequestBody ClientModels client){
        client.setId(id);
        ClientModels clientUpdate = clientService.update(client);
        return ResponseEntity.ok(clientUpdate);
    }


    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteById(@PathVariable int id){
        clientService.deleteById(id);
        return ResponseEntity.ok(null);
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String databaseError() throws IOException {
        // Nothing to do.  Returns the logical view name of an error page, passed
        // to the view-resolver(s) in usual way.
        // Note that the exception is NOT available to this view (it is not added
        // to the model) but see "Extending ExceptionHandlerExceptionResolver"
        // below.
        //response.sendError(460);
        return "databaseError";
    }
}
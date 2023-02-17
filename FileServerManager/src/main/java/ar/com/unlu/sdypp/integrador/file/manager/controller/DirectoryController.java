package ar.com.unlu.sdypp.integrador.file.manager.controller;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.Directory;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.DirectoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectoryController {

    @Autowired
    private DirectoryRepository directoryRepository;

    @PostMapping ("/Directorio")

    public Directory CreateRuta (@RequestBody String nombreRuta){
        var newRutaDirectory = new Directory();
        newRutaDirectory.setNombreRuta(nombreRuta);
        return directoryRepository.save(newRutaDirectory);
    }

    @GetMapping ("/RutasDirectorios")

    public Iterable <Directory> listDirectories (){
        return directoryRepository.findAll();
    }

}

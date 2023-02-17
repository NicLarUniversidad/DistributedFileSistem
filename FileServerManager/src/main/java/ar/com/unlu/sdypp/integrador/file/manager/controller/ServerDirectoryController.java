package ar.com.unlu.sdypp.integrador.file.manager.controller;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.Directory;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.DirectoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.cache.annotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerDirectoryController {

    @Autowired
    private DirectoryRepository directoryRepository;
    private Directory directory;

    @PostMapping("/RutaCreate")
    public Directory CreateDirectory (@RequestBody String nombreRuta){
        var newDirectorio = new Directory();
        newDirectorio.setNombreRuta(nombreRuta);
        newDirectorio.setActivo(false);
        return directoryRepository.save(newDirectorio);
    }


    @PostMapping("/RutaDelete")
    public String DeleteFile (@RequestBody Directory archivo){
        archivo.setActivo(false);


        return "Archivo eliminado";
    }


    @GetMapping("/Rutas")
    public Iterable <Directory> listFiles (){
        return directoryRepository.findAll();
    }


/*
    @Cacheable (cacheNames = "direcciones")
    public Directory findById(int id){
        Directory d = directoryRepository.stream().filter(it -> it.getId()==id).findFirst().orElseThrow(RuntimeException::new);
        logger.info("Item {} fue recuperado de la base", d.toString());
        return d;
    }
*/
}

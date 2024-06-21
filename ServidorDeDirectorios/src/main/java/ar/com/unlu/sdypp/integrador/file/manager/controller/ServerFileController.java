package ar.com.unlu.sdypp.integrador.file.manager.controller;

//import ar.com.file.system.servidorarchivo.demo.repositories.FileRepository;
import ar.com.unlu.sdypp.integrador.file.manager.cruds.FileCrud;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.cache.annotation;


public class ServerFileController {
    @Autowired
    private FileRepository fileRepository;
    private FileCrud fileCrud;

    @PostMapping("/ArchivoCreate")
    public FileCrud CreateFile (@RequestBody String nombreArchivo){
        var newFile = new FileCrud();
        newFile.setNombreArchivo(nombreArchivo);
        newFile.setNombreRutaDirectorio(" ");
        newFile.setTamaño("10kb");
        newFile.setFormato("txt");
        newFile.setTipo("1");
        newFile.setActivo(false);
        return newFile; //fileRepository.save(newFile, nombreArchivo);
    }


    @PostMapping("/ArchivoDelete")
    public String DeleteFile (@RequestBody FileCrud archivo){
        archivo.setActivo(false);
        return "Archivo eliminado";
    }


    @GetMapping("/Archivos")
    public Iterable <FileCrud> listFiles (){
        return null; //fileRepository.findAll();
    }


/*
    @Cacheable (cacheNames = "archivos")
    public File findById(int id){
        File f = this.fileRepository.stream().filter(it -> it.getId()==id).findFirst().orElseThrow(RuntimeException::new);
        logger.info("Item {} fue recuperado de la base", f.toString());
        return f;
    }
*/





}

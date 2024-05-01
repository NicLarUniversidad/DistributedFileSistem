package com.ar.sdypp.distributed_file_system.file_manager.repositories;

import com.ar.sdypp.distributed_file_system.file_manager.models.FileDetailsModel;
import com.ar.sdypp.distributed_file_system.file_manager.services.LoadBalancerService;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class FileRepository {

    @Autowired
    private LoadBalancerService loadBalancerService;

    public FileDetailsModel save(MultipartFile file, String username) throws IOException {

        byte data[] = file.getBytes();
        Path file0 = Paths.get(username + "/");
        Files.write(file0, data);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDetailsModel fileEntity = FileEntity.builder()
                .name(fileName)
                .username(file.getUsername)
                .build();
        return save(fileEntity);

        //TODO: Guardar archivo
        //Hay que elegir una estrategia para asignar el nombre a los archivos.
        //Se puede repetir el nombre con archivos de otro usuario?
        //Opcion 1: Username + nombre archivo + parte
        //Opcion 2: UUID asociado a usuario o nombre de archivo (tendria que guardarse la asociación a la bd)
        //Opcion 3: Id usuario + nombre/id archivo

        //Se puede guardar en el root de la aplicación, llamar el save sin hacer nada antes
        //Hay que elegir en qué tabla guardar, en el otro servicio:
        //      - Que el archivo es de determinado usuario
        //      - La ruta de donde se guarda el archivo
        //      - Si es un fragmento de archivo, hay que guardar de qué archivo es
        //TODO: Encriptado del contenido

        String size = calculateSize(file.getSize());
        var response = new FileDetailsModel("prueba", username, file.getName(), new Date(), size);
        return response;
    }


    public Optional<FileDetailsEntity> getFile(String id) throws FileNotFoundException{
        Optional<FileDetailsEntity> file = fileRepository.findById(id);
        if(file.isPresent()){
            return file;
        }
        throw new FileNotFoundException();
    }


    private File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
        multipart.transferTo(convFile);
        return convFile;
    }

    private String calculateSize(long size) {
        String response = "";
        response = size + " bytes";
        return response;
    }

    public FileSystemResource getFileById(String fileId) {
        File file = new File("FileManager.iml");
        //TODO: Decifrado del contenido
        return new FileSystemResource(file);
    }
}
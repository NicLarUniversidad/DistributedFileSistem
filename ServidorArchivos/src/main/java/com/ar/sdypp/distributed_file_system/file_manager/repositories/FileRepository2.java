package com.ar.sdypp.distributed_file_system.file_manager.repositories;

import com.ar.sdypp.distributed_file_system.file_manager.models.FileDetailsModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

//@Repository
public class FileRepository2 {

//    @Autowired
//    private LoadBalancerService loadBalancerService;
    //private StrongTextEncryptor textEncryptor;

    public FileRepository2() {
//        this.textEncryptor = new StrongTextEncryptor();
//        this.textEncryptor.setPassword("ultrasecreta");
    }

    public FileDetailsModel saveFile(MultipartFile file, String username) throws IOException {
        System.out.printf("a");
        byte data[] = file.getBytes();
        Path file0 = Paths.get(username + "/" + file.getOriginalFilename()); // username / nombre de archivo

        //String fileContain = new String(data, StandardCharsets.UTF_8);
        //var encryptData = textEncryptor.encrypt(fileContain).getBytes();

        Files.write(file0, data);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDetailsModel fileEntity = new FileDetailsModel();
//        FileDetailsModel.builder()
//                .name(fileName)
//                .username(username)
//                .id(file0.toString())
//                .build();
        fileEntity.setName(fileName);
        fileEntity.setUsername(username);
        fileEntity.setPath(file0.toString()); // Agrego path, por si en algún momento se cambia de estrategia
        fileEntity.setId(file0.toString());
        return fileEntity;

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

//        String size = calculateSize(file.getSize());
//        var response = new FileDetailsModel("prueba", username, file.getName(), new Date(), size);
//        return response;
    }


    public FileSystemResource getFile(String id) throws FileNotFoundException {
        //Comento porque no se declaró un método, la metadata del archivo se puede guardar en el otro servicio
//        Optional<FileDetailsEntity> file = fileRepository.findById(id);
//        if(file.isPresent()){
//            return file;
//        }
//        throw new FileNotFoundException();
        return null;
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

    public String getFileById(String fileId) throws IOException {
        // Por ahora el ID podría ser el path
        //File file = new File(fileId);
        var content = StreamUtils.copyToString( new ClassPathResource(fileId).getInputStream(), Charset.defaultCharset()  );
        //String plainText = textEncryptor.decrypt(content);
        //TODO: Decifrado del contenido
        return content;

    }
}
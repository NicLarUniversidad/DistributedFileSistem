package ar.com.unlu.sdypp.integrador.file.manager.servers;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.File;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public void save(MultipartFile file, String fileName) throws IOException {
        //TODO: Agregar lógica de cómo dividirlo


        this.fileRepository.save(file, fileName);
    }

    //public MultipartFile getFile(String fileId) {
        //TODO: Si tiene varias partes, acá se podrían ir recuperando y juntando
        //return this.fileRepository.getFile(fileId);
   // }

    //TODO: Si tiene varias partes, acá se podrían ir recuperando y juntando
    public File join(List<File> list) throws IOException {
        File outPutFile = File.createTempFile("temp-", "-unsplit", new File(TEMP_DIRECTORY));
        FileOutputStream fos = new FileOutputStream(outPutFile);
        for (File file : list) {
            Files.copy(file.toPath(), fos);
        }
        fos.close();
        return outPutFile;
    }


    /*public File uploadFile(MultipartFile file) {
        //Dividir el archivo en partes
        //Subir las partes a rabbit
        //Verificar que todas las partes se hayan guardado (Opcional)
        return null;
    }*/

    //publicar archivo en rabbit
    public void publishFile(String filePath, String queueName) throws IOException {
        // Leer el contenido del archivo
        Path path = Paths.get(filePath);
        byte[] fileContent = Files.readAllBytes(path);
        // Configurar las propiedades del mensaje
        MessageProperties properties = new MessageProperties();
        properties.setContentType("application/octet-stream"); // Tipo MIME para el contenido binario
        // Crear el mensaje con el contenido del archivo y las propiedades
        Message message = new Message(fileContent, properties);
        // Publicar el mensaje en RabbitMQ
        rabbitTemplate.send(queueName, message);
        System.out.println("Archivo " + filePath + " publicado en la cola " + queueName);
    }
}

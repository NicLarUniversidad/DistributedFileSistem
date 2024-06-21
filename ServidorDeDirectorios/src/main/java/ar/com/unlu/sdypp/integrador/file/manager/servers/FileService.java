package ar.com.unlu.sdypp.integrador.file.manager.servers;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.FileCrud;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.FileRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {

    public final String TEMP_DIRECTORY = "/tmp/";
    public static final Integer PART_NUMBERS = 10;

    @Autowired
    private FileRepository fileRepository;

    public void save(MultipartFile file, String username) throws IOException {
        var parts = this.fileRepository.splitByNumberOfFiles(file, PART_NUMBERS);
        for (var part : parts) {
            this.fileRepository.save(part, username);
        }

    }

    public MultipartFile getFile(String fileId) {
        //TODO: Si tiene varias partes, acá se podrían ir recuperando y juntando
        return this.fileRepository.getFile(fileId);
    }

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


    public FileCrud uploadFile(MultipartFile file, String username) throws IOException {
        //Dividir el archivo en partes
        //Subir las partes a rabbit
        //Verificar que todas las partes se hayan guardado (Opcional)
        this.save(file, username);
        return null;
    }

    //publicar archivo en rabbit
    @Autowired
    private RabbitTemplate rabbitTemplate;
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

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

    public MultipartFile getFile(String fileId) {
        //TODO: Si tiene varias partes, acá se podrían ir recuperando y juntando
        return this.fileRepository.getFile(fileId);
    }


    public File uploadFile(MultipartFile file) {
        //Dividir el archivo en partes
        //Subir las partes a rabbit
        //Verificar que todas las partes se hayan guardado (Opcional)
        return null;
    }
}

package ar.com.unlu.sdypp.integrador.file.manager.servers;

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

    public void save(MultipartFile file, String fileName) {
        //TODO: Agregar lógica de cómo dividirlo


        this.fileRepository.save(file, fileName);
    }

    public MultipartFile getFile(String fileId) {
        //TODO: Si tiene varias partes, acá se podrían ir recuperando y juntando
        return this.fileRepository.getFile(fileId);
    }
}

package ar.com.unlu.sdypp.integrador.file.manager.servers;

import ar.com.unlu.sdypp.integrador.file.manager.repositories.DirectoryServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@Service
public class DirectoryServerService {

    @Autowired
    private DirectoryServerRepository directoryServerRepository;

    public void save(MultipartFile file, String routeName) {
        this.directoryServerRepository.save(file, routeName);
    }
}

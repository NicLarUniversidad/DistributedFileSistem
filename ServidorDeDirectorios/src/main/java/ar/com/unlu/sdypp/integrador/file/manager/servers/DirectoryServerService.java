package ar.com.unlu.sdypp.integrador.file.manager.servers;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.ServerDirectory;
import ar.com.unlu.sdypp.integrador.file.manager.models.DirectoryServerModel;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.DirectoryServerRepository;
import ar.com.unlu.sdypp.integrador.file.manager.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DirectoryServerService {

    @Autowired
    private FileRepository fileServerRepository;

    @Autowired
    private DirectoryServerRepository directoryServerRepository;
    private DirectoryServerModel directoryServerModel;
    private ListRoute list;
/*
    public void setRoute (int id,String route) {
        this.directoryServerRepository.save(list);

    }

    public void deleteRoute (int id) {
        this.directoryServerRepository.delete(id);
    }

    public List<listRoute> showRoutes(ArrayList<Integer> fileid){
        List<listRoute> findAllByFileId = repository.findAllByFileId(fileid);
        return findAllByFileId;
    }

    public String saveFile (String nameRoute, File objFile){

        return "OK";
    }*/

    public void save(ServerDirectory model) {
        this.directoryServerRepository.save(model);
    }

    public Iterable<ServerDirectory> getAll() {
        return this.directoryServerRepository.findAll();
    }


}

package ar.com.unlu.sdypp.integrador.file.manager.repositories;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.ServerDirectory;
import ar.com.unlu.sdypp.integrador.file.manager.models.DirectoryServerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Repository
public interface DirectoryServerRepository extends CrudRepository<ServerDirectory, Integer> {

    //List<listRoute> findAllByFileId(ArrayList<Integer> fileid);

}

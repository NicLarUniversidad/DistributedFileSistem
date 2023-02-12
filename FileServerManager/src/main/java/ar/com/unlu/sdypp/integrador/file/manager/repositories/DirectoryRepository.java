package ar.com.file.system.servidorarchivo.demo.repositories;

import ar.com.file.system.servidorarchivo.demo.cruds.Directory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends CrudRepository <Directory, Integer>{
}

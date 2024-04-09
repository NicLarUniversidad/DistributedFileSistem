package ar.com.unlu.sdypp.integrador.file.manager.repositories;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.ServerFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerFileRepository extends CrudRepository <ServerFile, Integer>{
}

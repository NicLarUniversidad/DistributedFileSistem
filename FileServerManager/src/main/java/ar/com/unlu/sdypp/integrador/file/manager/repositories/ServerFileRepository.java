package ar.com.file.system.servidorarchivo.demo.repositories;

import ar.com.file.system.servidorarchivo.demo.cruds.ServerFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerFileRepository extends CrudRepository <ServerFile, Integer>{
}

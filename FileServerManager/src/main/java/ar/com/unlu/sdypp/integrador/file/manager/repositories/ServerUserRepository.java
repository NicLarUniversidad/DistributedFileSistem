package ar.com.file.system.servidorarchivo.demo.repositories;

import ar.com.file.system.servidorarchivo.demo.cruds.ServerUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerUserRepository extends CrudRepository <ServerUser, Integer>{
}

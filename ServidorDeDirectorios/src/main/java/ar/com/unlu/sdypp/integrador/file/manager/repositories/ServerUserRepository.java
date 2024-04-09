package ar.com.unlu.sdypp.integrador.file.manager.repositories;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.ServerUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerUserRepository extends CrudRepository<ServerUser, Integer>{

    ServerUser getFirstByNombre(String nombre);
}

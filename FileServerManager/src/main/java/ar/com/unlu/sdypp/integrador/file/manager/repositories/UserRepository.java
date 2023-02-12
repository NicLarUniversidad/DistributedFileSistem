package ar.com.file.system.servidorarchivo.demo.repositories;

import ar.com.file.system.servidorarchivo.demo.cruds.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository <User, Integer>{
}

package ar.com.unlu.sdypp.integrador.file.manager.repositories;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository <User, Integer>{
}

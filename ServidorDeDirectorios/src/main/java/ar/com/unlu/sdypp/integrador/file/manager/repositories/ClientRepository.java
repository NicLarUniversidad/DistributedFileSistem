package ar.com.unlu.sdypp.integrador.file.manager.repositories;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<User, Integer> {

    //@Query(value = "SELECT u.nombre, u.apellido FROM User u")
    List<User> findByFirstnameAndLastname(String firstname, String lastname);

    //@Query(value = "SELECT u.permiso FROM User u")
    List<User> findByPermiso(String permiso);
    List<User> findByUsername(String username);

   // @Query(value = "SELECT u.nombre, u.apellido FROM User u WHERE u.permiso=2")
   // List<Integer> getUsersWherePermiso2();
}

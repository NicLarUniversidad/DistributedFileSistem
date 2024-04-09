package ar.com.unlu.sdypp.integrador.file.manager.repositories;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientRepository {

    @Query(value = "SELECT u.nombre, u.apellido FROM User u")
    List<User> findByFirstnameAndLastname(String firstname, String lastname) {
        return null;
    }

    @Query(value = "SELECT u.permiso FROM User u")
    List<User> findBypermiso(String permiso) {
        return null;
    }

    @Query(value = "SELECT u.nombre, u.apellido FROM User u WHERE u.permiso=2")
    List<int> getUsersWherePermiso2() {
        return null;
    }
}

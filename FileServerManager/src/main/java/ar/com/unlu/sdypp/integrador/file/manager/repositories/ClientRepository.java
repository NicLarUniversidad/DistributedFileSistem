package ar.com.unlu.sdypp.integrador.file.manager.repositories;

import ar.com.unlu.sdypp.integrador.file.manager.servers.LoadBalancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class ClientRepository {

    @Query(value = "SELECT u.nombre, u.apellido FROM User u")
    List<User> findByFirstnameAndLastname(String firstname, String lastname);

    @Query(value = "SELECT u.permiso FROM User u")
    List<User> findBypermiso(String permiso);

    @Query(value = "SELECT u.nombre, u.apellido FROM User u WHERE u.permiso=2")
    List<int> getUsersWherePermiso2();
}

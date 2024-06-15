package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.repositories;

import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Repository//Se agrega esto para que Spring cree una instancia automáticamente y se pueda usar con @Autowired
public interface UserRepository extends CrudRepository<UserEntity, String> {//CurdRepository tiene un montón de
    //métodos que se crean automáticamente por reflexión
    UserEntity findByUsername(String username);//Se pueden definir un montón de métodos que SpringJPA genera
    //automáticamente, ver https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
}

package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.config;

import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.entities.UserEntity;
import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//Al implementar CommandLineRunner se ejecuta cuando inicia la aplicación...
@Component
public class Seeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        //Si el usuario sdypp no existe se crea.
        var savedUser = this.userRepository.findByUsername("sdypp");
        if (savedUser == null) {
            UserEntity user = new UserEntity();
            user.setUsername("sdypp");
            //Contraseña: "supersecreta"
            //Generador online: https://bcrypt.online/
            user.setPassword("$2y$10$duSbtbew5ebb/y4bT95Ole6yIQICx0VSF5eGe7Pi8QkWAICrRA0ZG");
            this.userRepository.save(user);
        }
    }
}

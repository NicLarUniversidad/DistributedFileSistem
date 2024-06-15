package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//Declaración genérica de aplicación Spring-boot, inicia la aplicación con el framework...
//@SpringBootApplication
@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
public class DistributedFileSystemFacadeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributedFileSystemFacadeApplication.class, args);
	}

}

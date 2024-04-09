package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.config;

import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.entities.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Configuración de seguridad...
@Service
public class FileSystemUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    private Logger logger = LoggerFactory.getLogger(FileSystemUsernamePwdAuthenticationProvider.class);
    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Acá está la lógica del login, que en este caso es por basic auth...
    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        UserEntity customer = UserRepository.findByUsername(username);
        this.logger.info("Se intenta autenticar");
        if (customer != null) {
            if (passwordEncoder.matches(pwd, customer.getPassword())) {
                this.logger.info("Contraseña correcta");
                List<GrantedAuthority> authorities = new ArrayList<>();
                //authorities.add(new SimpleGrantedAuthority(customer.getRoles()));
                return new UsernamePasswordAuthenticationToken(username, pwd, authorities);
            } else {
                String msg = String.format("Contraseña incorrecta para el usuario %s", username);
                this.logger.warn(msg);
                throw new BadCredentialsException(msg);
            }
        }else {
            String msg = String.format("No se encontró al usuario (%s) en la base de datos", username);
            this.logger.warn(msg);
            throw new BadCredentialsException(msg);
        }
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
    }
}


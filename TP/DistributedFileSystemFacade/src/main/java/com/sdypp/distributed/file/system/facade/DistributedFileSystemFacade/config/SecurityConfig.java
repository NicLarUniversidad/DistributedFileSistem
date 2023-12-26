package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

//Otras configuraciones de seguridad, no recuerdo por qué separé las clases
@Configuration
public class SecurityConfig {

    //Acá se definen los recursos o URL que van a requerir estar logeado o no.
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        /**
         * Custom configurations as per our requirement
         */
        http.authorizeHttpRequests((auth) -> auth
                .antMatchers("/myAccount", "/health").authenticated()
                .antMatchers("/").permitAll()
        ).httpBasic(Customizer.withDefaults());
        return http.build();

    }

    //Acá se define el algoritmo con el que se encriptan las contraseñas
    //Se usa BCrypt con nivel de fuerza 10...
    //Generador online: https://bcrypt.online/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

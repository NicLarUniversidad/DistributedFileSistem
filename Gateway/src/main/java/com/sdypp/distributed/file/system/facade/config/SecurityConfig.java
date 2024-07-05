//package com.sdypp.distributed.file.system.facade.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.sql.DataSource;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
////Otras configuraciones de seguridad, no recuerdo por qué separé las clases
//@Configuration
//public class SecurityConfig {
//
//    //Acá se definen los recursos o URL que van a requerir estar logeado o no.
//    @Bean
//    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//
//        /**
//         * Custom configurations as per our requirement
//         */
//        http.authorizeHttpRequests(authorize -> authorize
//                                .requestMatchers("/**").permitAll()
//                        //.anyRequest().authenticated()
//                )
//                //.cors().disable()
//                .httpBasic(withDefaults());
////        http.authorizeHttpRequests(auth -> auth
////                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Quita seguridad para el cors
////                .antMatchers(HttpMethod.GET, "/files", "/myAccount").authenticated()
////                .antMatchers(HttpMethod.POST, "/files").authenticated()
////                .antMatchers("/health").permitAll()
////        ).httpBasic(Customizer.withDefaults())
////                .csrf().disable();
//        return http.build();
//
//    }
//
//    //Acá se define el algoritmo con el que se encriptan las contraseñas
//    //Se usa BCrypt con nivel de fuerza 10...
//    //Generador online: https://bcrypt.online/
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//}

package ar.com.unlu.sdypp.integrador.file.manager.cruds;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name="Usuarios")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String contrasenia;
    private String job_title;
    private String firstname;
    private String lastname;
    private String email;
    private String direccion;;
    private boolean cambiar_permisos;
    private String permiso;
    private Boolean activo;
    private String username;
}

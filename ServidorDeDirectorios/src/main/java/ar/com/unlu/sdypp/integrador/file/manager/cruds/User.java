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
    Integer ID;
    String firstname;
    String lastname;
    String email;
    String direccion;;
    String permiso;
    Boolean activo;
}

package ar.com.unlu.sdypp.integrador.file.manager.cruds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Data
@Entity(name="ServerUser")
@AllArgsConstructor
@NoArgsConstructor
public class ServerUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer ID;
    String nombre;
}

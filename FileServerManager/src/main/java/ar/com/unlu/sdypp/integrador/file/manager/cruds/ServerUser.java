package ar.com.file.system.servidorarchivo.demo.cruds;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Generated;

@Data
@Entity(name="ServerUser")
public class ServerUser {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Integer ID;
    String nombre;
}

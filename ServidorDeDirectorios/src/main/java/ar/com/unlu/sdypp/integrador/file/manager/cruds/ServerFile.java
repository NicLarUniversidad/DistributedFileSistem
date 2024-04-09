package ar.com.unlu.sdypp.integrador.file.manager.cruds;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@Entity(name="ServerFile")
@AllArgsConstructor
@NoArgsConstructor
public class ServerFile {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Integer ID;
    String nombre;
    Boolean primario;
}

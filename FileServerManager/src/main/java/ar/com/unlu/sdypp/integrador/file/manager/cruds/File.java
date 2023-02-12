package ar.com.file.system.servidorarchivo.demo.cruds;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Generated;

@Data
@Entity(name="Archivo")
public class File {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Integer ID;
    String nombreArchivo;
    String nombreRutaDirectorio;
    String tamaño;
    String formato;
    String tipo; //si es de lectura, escritura o ambas
    Boolean activo;


}

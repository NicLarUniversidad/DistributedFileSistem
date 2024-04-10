package ar.com.unlu.sdypp.integrador.file.manager.cruds;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@Entity(name="Archivo")
@AllArgsConstructor
@NoArgsConstructor
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

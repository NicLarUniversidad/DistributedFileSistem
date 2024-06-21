package ar.com.unlu.sdypp.integrador.file.manager.cruds;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name="Archivo")
@AllArgsConstructor
@NoArgsConstructor
public class FileCrud {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer ID;
    private String nombreArchivo;
    private String nombreRutaDirectorio;
    private String tamaño;
    private String formato;
    private String tipo; //si es de lectura, escritura o ambas
    private Boolean activo;


}

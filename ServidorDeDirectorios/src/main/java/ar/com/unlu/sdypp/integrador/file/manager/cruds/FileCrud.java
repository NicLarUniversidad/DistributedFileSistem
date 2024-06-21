package ar.com.unlu.sdypp.integrador.file.manager.cruds;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FilePartCrud> parts = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private UserCrud user;


}

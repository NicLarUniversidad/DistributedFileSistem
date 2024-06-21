package ar.com.unlu.sdypp.integrador.file.manager.cruds;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FilePartCrud {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nombre;
    private Integer orden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private FileCrud originalFile;
}

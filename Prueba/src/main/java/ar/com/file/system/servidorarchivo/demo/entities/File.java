package ar.com.file.system.servidorarchivo.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 50, name = "ubicacion")
    private String campo1;
}

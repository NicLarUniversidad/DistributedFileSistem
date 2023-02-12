package ar.com.file.system.servidorarchivo.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TestEntity {
    @Id
    private int id;
    private String name;
    private String path;
    private int usuario;

}

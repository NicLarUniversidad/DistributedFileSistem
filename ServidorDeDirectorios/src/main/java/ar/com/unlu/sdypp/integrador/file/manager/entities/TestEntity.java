package ar.com.unlu.sdypp.integrador.file.manager.entities;


import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Data
public class TestEntity {
    @Id
    private int id;
    private String name;
    private String path;
    private int usuario;

}

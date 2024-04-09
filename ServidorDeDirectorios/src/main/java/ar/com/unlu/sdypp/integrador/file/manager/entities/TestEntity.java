package ar.com.unlu.sdypp.integrador.file.manager.entities;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class TestEntity {
    @Id
    private int id;
    private String name;
    private String path;
    private int usuario;

}

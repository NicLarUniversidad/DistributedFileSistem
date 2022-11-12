package ar.com.file.system.servidorarchivo.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TestEntity {
    @Id
    private int id;
    private String name;
}

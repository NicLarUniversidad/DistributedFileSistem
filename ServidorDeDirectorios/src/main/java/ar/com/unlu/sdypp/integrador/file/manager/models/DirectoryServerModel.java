package ar.com.unlu.sdypp.integrador.file.manager.models;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

@Data
public class DirectoryServerModel {

    private Integer id;
    private String path;
    private Integer userId;
    private boolean saturado;
}

package ar.com.unlu.sdypp.integrador.file.manager.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class DirectoryServerModel {

    private Integer id;
    private String path;
    private Integer userId;
    private boolean saturado;
}

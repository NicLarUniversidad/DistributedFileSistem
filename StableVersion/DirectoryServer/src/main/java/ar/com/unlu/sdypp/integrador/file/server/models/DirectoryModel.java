package ar.com.unlu.sdypp.integrador.file.server.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class DirectoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String route;
    private String name;
    private String path;
    private Integer userId;
    private Date creationDate;
    private Date lastModification;
}

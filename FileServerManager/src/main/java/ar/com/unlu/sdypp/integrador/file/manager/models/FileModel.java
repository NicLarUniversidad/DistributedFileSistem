package ar.com.unlu.sdypp.integrador.file.manager.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class FileModel {
    private String name;
    private String content;
    private String path;
    private float size;
    private Date creationDate;
    private Date lastModification;
}

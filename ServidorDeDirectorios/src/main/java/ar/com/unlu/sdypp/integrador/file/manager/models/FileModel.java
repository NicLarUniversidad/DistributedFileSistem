package ar.com.unlu.sdypp.integrador.file.manager.models;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

package ar.com.unlu.sdypp.integrador.file.manager.models;

import lombok.Data;

import java.util.Date;

@Data
public class FileModel {
    public static final String GUARDADO = "guardado";
    public static final String MODIFICACION = "modificacion";

    private String name;
    private byte[] content;
    private String path;
    private float size;
    private Date creationDate;
    private Date lastModification;
    private String username;
    private String messageType;
}

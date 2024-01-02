package ar.com.unlu.sdypp.integrador.file.manager.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class ClientModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String contrasenia;
    private String name;
    private String apellido;
    private String job_title;
    private boolean cambiar_permisos;
    private int permiso; /* 1- read only
                            2- read and write */
}

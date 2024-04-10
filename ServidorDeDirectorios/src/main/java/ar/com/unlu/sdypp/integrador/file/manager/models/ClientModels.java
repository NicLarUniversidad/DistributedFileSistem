package ar.com.unlu.sdypp.integrador.file.manager.models;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

@Data
public class ClientModels {
    private int id;
    private String contrasenia;
    private String nombre;
    private String apellido;
    private String job_title;
    private boolean cambiar_permisos;
    private int permiso; /* 1- read only
                            2- read and write */
}

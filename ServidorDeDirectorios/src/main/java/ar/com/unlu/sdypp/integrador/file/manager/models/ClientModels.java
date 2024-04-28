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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public boolean isCambiar_permisos() {
        return cambiar_permisos;
    }

    public void setCambiar_permisos(boolean cambiar_permisos) {
        this.cambiar_permisos = cambiar_permisos;
    }

    public int getPermiso() {
        return permiso;
    }

    public void setPermiso(int permiso) {
        this.permiso = permiso;
    }
}

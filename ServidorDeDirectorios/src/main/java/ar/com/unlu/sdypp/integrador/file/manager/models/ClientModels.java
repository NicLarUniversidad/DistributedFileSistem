package ar.com.unlu.sdypp.integrador.file.manager.models;

import lombok.Data;

@Data
public class ClientModels {
    private int id;
    private String contrasenia;
    private String firstName;
    private String lastName;
    private String job_title;
    private String email;
    private boolean cambiar_permisos;
    private int permiso; /* 1- read only
                            2- read and write */
    private String direccion;;
    private Boolean activo;
    private String username;


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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

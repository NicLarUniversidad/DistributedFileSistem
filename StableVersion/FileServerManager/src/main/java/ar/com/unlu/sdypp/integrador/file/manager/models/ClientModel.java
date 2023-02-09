package ar.com.unlu.sdypp.integrador.file.manager.models;

import lombok.Data;

@Data
public class ClientModel {
    private int id;
    private String name;
    private String job_title;
    private boolean cambiar_permisos;
    private int permiso; /* 1- read only
                            2- read and write */
}

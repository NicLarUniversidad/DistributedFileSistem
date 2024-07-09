package com.sdypp.distributed.file.system.facade.models;

import lombok.Data;

//Hay que definir clases que represente lo que se recibe o se devuelve de la API
@Data//Con esto se generan (por reflexión y en tiempo de ejecución) getters, setters, y otros métodos útiles...
public class FileModel {
    private Integer ID;
    private String nombreArchivo;
    private String nombreRutaDirectorio;
    private String tamaño;
    private String formato;
    private String tipo; //si es de lectura, escritura o ambas
    private Boolean activo;
}

package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.models;

import lombok.Data;

//Hay que definir clases que represente lo que se recibe o se devuelve de la API
@Data//Con esto se generan (por reflexión y en tiempo de ejecución) getters, setters, y otros métodos útiles...
public class FileModel {
    private String fileName;
}

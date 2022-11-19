package ar.com.unlu.sdypp.integrador.file.manager.models;

import lombok.Data;

@Data
public class DirectoryServerModel {
    private int id;
    private String route;
    private String[] files;
}

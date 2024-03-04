package ar.com.unlu.sdypp.integrador.file.manager.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class GestorServerDirectoryModel {
    private int id;
    private boolean status;
    private boolean saturado;
}

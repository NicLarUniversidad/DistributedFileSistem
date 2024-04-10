package ar.com.unlu.sdypp.integrador.file.manager.models;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

@Data
public class GestorServerDirectoryModel {
    private int id;
    private boolean status;
    private boolean saturado;
}

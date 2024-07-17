package ar.com.unlu.sdypp.integrador.file.manager.models;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.FilePartCrud;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PartsModel {
    private List<FilePartCrud> parts = new ArrayList<>();
}

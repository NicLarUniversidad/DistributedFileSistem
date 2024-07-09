package ar.com.unlu.sdypp.integrador.file.manager.models;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.FileCrud;
import lombok.Data;

import java.util.List;

@Data
public class FileListModel {
    private List<FileCrud> files;
}

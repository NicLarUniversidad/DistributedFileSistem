package ar.com.unlu.sdypp.integrador.file.manager.models;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.FileCrud;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class FileListModel {
    private Page<FileCrud> files;
}

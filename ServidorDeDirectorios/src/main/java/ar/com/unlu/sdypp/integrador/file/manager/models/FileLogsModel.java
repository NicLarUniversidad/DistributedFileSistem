package ar.com.unlu.sdypp.integrador.file.manager.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FileLogsModel {
    private List<FileDownloadLog> fileDownloadLog = new ArrayList<>();
}

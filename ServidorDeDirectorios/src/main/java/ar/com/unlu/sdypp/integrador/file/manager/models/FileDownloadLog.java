package ar.com.unlu.sdypp.integrador.file.manager.models;

import ar.com.unlu.sdypp.integrador.file.manager.cruds.TimeLogs;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FileDownloadLog {
    private TimeLogs fileLog;
    private List<TimeLogs> partsLogs = new ArrayList<>();
}

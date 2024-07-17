package com.sdypp.distributed.file.system.facade.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FileLogsModel {
    private List<FileDownloadLog> fileDownloadLog = new ArrayList<>();
}

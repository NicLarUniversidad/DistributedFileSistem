package com.ar.sdypp.distributed_file_system.file_manager.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
public class FilesDetailsModel {
    private List<FileDetailsModel> files;

    public FilesDetailsModel() {
        this.files = new LinkedList<>();
    }
}

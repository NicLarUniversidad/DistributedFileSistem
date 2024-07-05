package com.sdypp.distributed.file.system.facade.models;

import lombok.Data;

import java.util.List;

@Data
public class FilesDetailModel {
    private List<FileDetailsModel> files;
}

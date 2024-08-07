package com.sdypp.distributed.file.system.facade.models;

import lombok.Data;

@Data
public class FilesDetailModel {
    private RestPage<FileModel> files;
}

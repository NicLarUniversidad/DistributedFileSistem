package com.sdypp.distributed.file.system.facade.DistributedFileSystemFacade.models;

import lombok.Data;

import java.util.Date;

@Data
public class FileDetailsModel {
    private String name;
    private String size;
    private Date uploadedDate;
}

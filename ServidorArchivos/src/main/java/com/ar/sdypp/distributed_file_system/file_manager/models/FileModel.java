package com.ar.sdypp.distributed_file_system.file_manager.models;

import lombok.Data;

import java.util.Date;

@Data
public class FileModel {
    private String name;
    private String content;
    private String path;
    private float size;
    private Date creationDate;
    private Date lastModification;
    private String username;
}

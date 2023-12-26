package com.ar.sdypp.distributed_file_system.file_manager.models;

import lombok.Data;

import java.util.Date;

@Data
public class FileDetailsModel {
    private String id;
    private String name;
    private String size;
    private Date uploadedDate;
    private String username;
}

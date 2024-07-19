package com.ar.sdypp.distributed_file_system.file_manager.models;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDetailsModel implements Serializable{
    private String id;
    private String name;
    private String size;
    private Date uploadedDate;
    private String username;
    private String path;
}

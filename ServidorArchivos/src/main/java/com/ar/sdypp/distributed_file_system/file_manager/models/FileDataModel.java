package com.ar.sdypp.distributed_file_system.file_manager.models;

import lombok.Data;

@Data
public class FileDataModel {

    private byte[] data;
    private String bucketName;
}

package com.ar.sdypp.distributed_file_system.file_manager.models;

import lombok.Data;

@Data
public class SettingsModel {
    private String rabbitUri;
    private String storageProject;
    private String storageBuckets;
}

package com.sdypp.distributed.file.system.facade.models.settings;

import lombok.Data;

@Data
public class ConfigurationSettingsModel {
    private String redisHost;
    private String redisPort;
    private String redisPolicy;
    private String redisMaxMemory;
    private String fileServerHost;
}

package ar.com.unlu.sdypp.integrador.file.manager.models;

import lombok.Data;

@Data
public class ConfigurationSettingsModel {
    private String databaseUrl;
    private String rabbitUri;
    private String rabbitQueueName;
    private String rabbitExchangeName;
    private String fileServerUrl;
}

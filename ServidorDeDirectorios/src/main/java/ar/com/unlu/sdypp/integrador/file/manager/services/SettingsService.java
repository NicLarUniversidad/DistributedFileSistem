package ar.com.unlu.sdypp.integrador.file.manager.services;

import ar.com.unlu.sdypp.integrador.file.manager.models.ConfigurationSettingsModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SettingsService {

    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${sdypp.rabbitmq.uri}")
    private String rabbitUri;
    @Value("${sdypp.rabbitmq.queue.name}")
    private String rabbitQueueName;
    @Value("${sdypp.rabbitmq.queue.exchange-name}")
    private String rabbitExchangeName;
    @Value("${sdypp.file.server.host}")
    private String fileServerUrl;

    public ConfigurationSettingsModel getSettings() {
        ConfigurationSettingsModel settingsModel = new ConfigurationSettingsModel();
        settingsModel.setDatabaseUrl(this.databaseUrl);
        String rabbitUri = this.rabbitUri;
        var parts = rabbitUri.split(":");
        parts[2] = parts[2].replace(parts[2].substring(0, parts[2].lastIndexOf("@")), "___");
        List<String> list = Arrays.asList(parts);
        settingsModel.setRabbitUri(list.stream()
                .map(str -> String.valueOf(str))
                .collect(Collectors.joining(":")));
        settingsModel.setRabbitQueueName(this.rabbitQueueName);
        settingsModel.setRabbitExchangeName(this.rabbitExchangeName);
        settingsModel.setFileServerUrl(this.fileServerUrl);
        return settingsModel;
    }
}

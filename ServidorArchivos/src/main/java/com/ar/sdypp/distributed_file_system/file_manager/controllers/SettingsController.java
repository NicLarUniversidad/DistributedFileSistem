package com.ar.sdypp.distributed_file_system.file_manager.controllers;

import com.ar.sdypp.distributed_file_system.file_manager.models.SettingsModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SettingsController {

    @Value("${sdypp.rabbitmq.uri}")
    private String rabbitUri;
    @Value("${sdypp.storage.project}")
    private String storageProject;
    @Value("${sdypp.storage.buckets}")
    private String storageBuckets;

    @GetMapping("/settings")
    public SettingsModel getSettings() {
        SettingsModel settingsModel = new SettingsModel();
        String rabbitUri = this.rabbitUri;
        var parts = rabbitUri.split(":");
        parts[2] = parts[2].replace(parts[2].substring(0, parts[2].lastIndexOf("@")), "___");
        List<String> list = Arrays.asList(parts);
        settingsModel.setRabbitUri(list.stream()
                .map(str -> String.valueOf(str))
                .collect(Collectors.joining(":")));
        settingsModel.setStorageProject(storageProject);
        settingsModel.setStorageBuckets(storageBuckets);
        return settingsModel;
    }
}

package com.sdypp.distributed.file.system.facade.controllers;

import com.sdypp.distributed.file.system.facade.models.settings.ConfigurationSettingsModel;
import com.sdypp.distributed.file.system.facade.services.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigurationController {

    private final SettingsService settingsService;

    @Autowired
    public ConfigurationController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping("/settings")
    public ConfigurationSettingsModel getSettings() {
        return this.settingsService.getCurrentSettings();
    }
}

package main.controller;

import main.api.response.InitResponse;
import main.api.response.SettingsResponse;
import main.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GeneralController {

    private final SettingsService settingsService;
    private final InitResponse initResponse;

    @Autowired
    public GeneralController(final SettingsService settingsService, final InitResponse initResponse) {
        this.settingsService = settingsService;
        this.initResponse = initResponse;
    }

    @GetMapping("/settings")
    private SettingsResponse getSettings() {
        return settingsService.getGlobalSettings();
    }

    @GetMapping("/init")
    private InitResponse getInitResponse() {
        return initResponse;
    }
}

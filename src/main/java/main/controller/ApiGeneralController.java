package main.controller;

import lombok.AllArgsConstructor;
import main.api.response.InitResponse;
import main.api.response.SettingsResponse;
import main.service.SettingsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final InitResponse initResponse;

    @GetMapping("/settings")
    private SettingsResponse settings(){
        return settingsService.getGlobalSettings();
    }

    @GetMapping("/init")
    private InitResponse init(){
        return initResponse;
    }
}

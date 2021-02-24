package main.controller;

import lombok.AllArgsConstructor;
import main.api.response.AuthResponse;
import main.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiAuthController {

    private final AuthService authService;

    @GetMapping("/auth/check")
    private AuthResponse authResponse() {
        return authService.authResponse();
    }
}

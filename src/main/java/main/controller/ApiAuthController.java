package main.controller;

import main.api.response.AuthResponse;
import main.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiAuthController {


    private final AuthService authService;

    public ApiAuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/auth/check")
    private AuthResponse authResponse(){
        return authService.authResponse();
    }
}

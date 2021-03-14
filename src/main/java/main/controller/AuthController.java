package main.controller;

import main.api.response.AuthRegisterResponse;
import main.api.response.AuthResponse;
import main.dto.NewUserRequest;
import main.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/auth/check")
    private AuthResponse getAuthResponse() {
        return authService.getAuthResponse();
    }

    @PostMapping("/auth/register")
    private AuthRegisterResponse getAuthRegisterResponse(@RequestBody final NewUserRequest newUserRequest) {
                return authService.getAuthRegisterResponse(newUserRequest);
    }

}

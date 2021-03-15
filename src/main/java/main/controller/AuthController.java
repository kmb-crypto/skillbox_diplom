package main.controller;

import main.api.request.LoginRequest;
import main.api.response.AuthRegisterResponse;
import main.api.response.AuthResponse;
import main.api.request.RegisterUserRequest;
import main.api.response.LoginResponse;
import main.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private AuthRegisterResponse getAuthRegisterResponse(@RequestBody final RegisterUserRequest registerUserRequest) {
        return authService.getAuthRegisterResponse(registerUserRequest);
    }

    @PostMapping("/auth/login")
    private ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new LoginResponse());
    }

}

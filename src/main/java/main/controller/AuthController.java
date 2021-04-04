package main.controller;

import main.api.request.LoginRequest;
import main.api.response.AuthRegisterResponse;
import main.api.response.AuthResponse;
import main.api.request.RegisterUserRequest;
import main.api.response.LogoutResponse;
import main.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/check")
    public ResponseEntity<AuthResponse> getAuthResponse(final Principal principal) {
        return ResponseEntity.ok(authService.checkAuthResponse(principal));
    }

    @GetMapping("/logout")
    public ResponseEntity<LogoutResponse> logout() {

        return ResponseEntity.ok(authService.getLogoutResponse());
    }

    @PostMapping("/register")
    public AuthRegisterResponse getAuthRegisterResponse(@RequestBody final RegisterUserRequest registerUserRequest) {
        return authService.getAuthRegisterResponse(registerUserRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody final LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.getLoginAuthResponse(loginRequest));
    }

}

package main.controller;

import main.api.response.AuthRegisterResponse;
import main.api.response.AuthResponse;
import main.dto.NewUserDto;
import main.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiAuthController {

    private final AuthService authService;

    @Autowired
    public ApiAuthController(final AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/auth/check")
    private AuthResponse authResponse() {
        return authService.authResponse();
    }

    @PostMapping("/auth/register")
    private AuthRegisterResponse authRegisterResponse(@RequestBody final NewUserDto newUserDto) {
                return authService.authRegisterResponse(newUserDto);
    }

}

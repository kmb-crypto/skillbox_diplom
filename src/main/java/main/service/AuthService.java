package main.service;

import main.api.response.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public AuthResponse authResponse(){
        AuthResponse authResponse = new AuthResponse();
        return authResponse;
    }
}

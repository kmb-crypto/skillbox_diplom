package main.service;

import main.api.response.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public AuthResponse authResponse() {
        AuthResponse authResponse = new AuthResponse();
        return authResponse;
    }
}

//{
//        "result": true,
//        "user": {
//        "id": 576,
//        "name": "Дмитрий Петров",
//        "photo": "/avatars/ab/cd/ef/52461.jpg",
//        "email": "petrov@petroff.ru",
//        "moderation": true,
//        "moderationCount": 56,
//        "settings": true
//        }
//}

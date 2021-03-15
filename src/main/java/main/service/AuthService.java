package main.service;

import main.api.response.AuthRegisterResponse;
import main.api.response.AuthResponse;
import main.api.request.RegisterUserRequest;
import main.model.User;
import main.repository.CaptchaRepository;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final CaptchaRepository captchaRepository;
    private final PasswordEncoder passwordEncoder;

    private static final int PASSWORD_LENGTH_THRESHOLD = 6;

    @Autowired
    public AuthService(final UserRepository userRepository, final CaptchaRepository captchaRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.captchaRepository = captchaRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public AuthResponse getAuthResponse() {
        AuthResponse authResponse = new AuthResponse();
        return authResponse;
    }

    public AuthRegisterResponse getAuthRegisterResponse(final RegisterUserRequest registerUserRequest) {
        String name = registerUserRequest.getName().trim().replaceAll("\\s+", " ");
        HashMap<String, String> errors = new HashMap<>();
        boolean result = true;
        if (userRepository.findByEmail(registerUserRequest.getEmail()).isPresent()) {
            errors.put("email", "Этот e-mail уже зарегистрирован");
            result = false;
        }

        if (!checkName(name)) {
            errors.put("name", "Имя указано не верно");
            result = false;
        }

        if (registerUserRequest.getPassword().length() < PASSWORD_LENGTH_THRESHOLD) {
            errors.put("password", "Пароль короче 6-ти символов");
            result = false;
        }

        if (captchaRepository.getCaptchaCode(registerUserRequest.getCaptcha(), registerUserRequest.getCaptchaSecret()) == null) {
            errors.put("captcha", "Код с картинки введён неверно");
            result = false;
        }

        if (result) {

            addNewUser(userRepository, registerUserRequest);
            return new AuthRegisterResponse(result);

        } else {
            return new AuthRegisterResponse(result, errors);
        }
    }

    private boolean checkName(final String name) {

        if (name.replaceAll("[a-zа-яёA-ZА-ЯЁ\\s]+", "").equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private void addNewUser(final UserRepository userRepository, final RegisterUserRequest registerUserRequest) {
        User user = new User();
        user.setEmail(registerUserRequest.getEmail());
        user.setName(registerUserRequest.getName());
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        user.setRegTime(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
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

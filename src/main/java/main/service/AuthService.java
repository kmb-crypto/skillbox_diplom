package main.service;

import main.api.response.AuthRegisterResponse;
import main.api.response.AuthResponse;
import main.dto.NewUserDto;
import main.model.User;
import main.repository.CaptchaRepository;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final CaptchaRepository captchaRepository;

    private static final int PASSWORD_LENGTH_THRESHOLD = 6;

    @Autowired
    public AuthService(final UserRepository userRepository, final CaptchaRepository captchaRepository) {
        this.userRepository = userRepository;
        this.captchaRepository = captchaRepository;
    }


    public AuthResponse authResponse() {
        AuthResponse authResponse = new AuthResponse();
        return authResponse;
    }

    public AuthRegisterResponse authRegisterResponse(final NewUserDto newUserDto) {
        String name = newUserDto.getName().trim().replaceAll("\\s+", " ");
        HashMap<String, String> errors = new HashMap<>();
        boolean result = true;
    //    if (userRepository.getUserByEmail(newUserDto.getEmail()) != null) {
            if (userRepository.findByEmail(newUserDto.getEmail()) != null) {
            errors.put("email", "Этот e-mail уже зарегистрирован");
            result = false;
        }

        if (!checkName(name)) {
            errors.put("name", "Имя указано не верно");
            result = false;
        }

        if (newUserDto.getPassword().length() < PASSWORD_LENGTH_THRESHOLD) {
            errors.put("password", "Пароль короче 6-ти символов");
            result = false;
        }

        if (captchaRepository.getCaptchaCode(newUserDto.getCaptcha(), newUserDto.getCaptchaSecret()) == null) {
            errors.put("captcha", "Код с картинки введён неверно");
            result = false;
        }

        if (result) {

            addNewUser(userRepository, newUserDto);
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

    private void addNewUser(final UserRepository userRepository, final NewUserDto newUserDto) {
        User user = new User();
        user.setEmail(newUserDto.getEmail());
        user.setName(newUserDto.getName());
        user.setPassword(newUserDto.getPassword());
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

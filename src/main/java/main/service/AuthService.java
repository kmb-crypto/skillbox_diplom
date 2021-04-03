package main.service;

import main.api.request.LoginRequest;
import main.api.response.AuthRegisterResponse;
import main.api.response.AuthResponse;
import main.api.request.RegisterUserRequest;
import main.api.response.LogoutResponse;
import main.dto.AuthResponseUserDto;
import main.model.User;
import main.repository.CaptchaRepository;
import main.repository.PostRepository;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final CaptchaRepository captchaRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    static final int PASSWORD_LENGTH_THRESHOLD = 6;

    @Autowired
    public AuthService(final UserRepository userRepository,
                       final CaptchaRepository captchaRepository,
                       final PostRepository postRepository,
                       final PasswordEncoder passwordEncoder,
                       final AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.captchaRepository = captchaRepository;
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    public AuthResponse checkAuthResponse(final Principal principal) {
        if (principal == null) {
            return new AuthResponse();
        } else {
            return user2authResponseUserDto(userRepository.findByEmail(principal.getName()).get());
        }
    }

    public AuthResponse getLoginAuthResponse(final LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());
        if (optionalUser.isEmpty()
                || !passwordEncoder.matches(loginRequest.getPassword(), optionalUser.get().getPassword())) {
            return new AuthResponse();
        } else {
            Authentication auth = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);

            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User) auth.getPrincipal();

            return user2authResponseUserDto(optionalUser.get());
        }
    }

    public LogoutResponse getLogoutResponse() {
        SecurityContextHolder.clearContext();
        return new LogoutResponse(true);
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

            addNewUser(registerUserRequest);
            return new AuthRegisterResponse(result);

        } else {
            return new AuthRegisterResponse(result, errors);
        }
    }
    boolean checkName(final String name) {
        return name.replaceAll("[a-zа-яёA-ZА-ЯЁ\\s]+", "").equals("");
    }

    private void addNewUser(final RegisterUserRequest registerUserRequest) {
        User user = new User();
        user.setEmail(registerUserRequest.getEmail());
        user.setName(registerUserRequest.getName());
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        user.setRegTime(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
    }

    private AuthResponse user2authResponseUserDto(User currentUser) {


        AuthResponseUserDto authResponseUserDto = new AuthResponseUserDto();
        authResponseUserDto.setId(currentUser.getId());
        authResponseUserDto.setName(currentUser.getName());
        authResponseUserDto.setPhoto(currentUser.getPhoto());
        authResponseUserDto.setEmail(currentUser.getEmail());
        authResponseUserDto.setModeration(currentUser.getIsModerator() == 1);
        authResponseUserDto.setModerationCount(
                currentUser.getIsModerator() == 1 ? postRepository.countAllNewPosts() : 0);
        authResponseUserDto.setSettings(currentUser.getIsModerator() == 1);
        return new AuthResponse(true, authResponseUserDto);

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

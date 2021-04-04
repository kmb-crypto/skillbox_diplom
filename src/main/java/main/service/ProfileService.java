package main.service;

import main.api.response.ImageLoadResponse;
import main.api.response.ProfileEditResponse;
import main.model.User;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.Optional;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;

    @Value("${avatars.path}")
    private String avatarsPath;

    static int AVATAR_CROP_SIZE = 36;

    public ProfileService(final UserRepository userRepository,
                          final AuthService authService,
                          final FileService fileService,
                          final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.fileService = fileService;
        this.passwordEncoder = passwordEncoder;
    }

    public ProfileEditResponse ProfileEdit(final MultipartFile multipartFile,
                                           String name, final String email, final String password,
                                           final Integer removePhoto, final Principal principal) {
        HashMap<String, String> errors = new HashMap<>();
        name = name.trim().replaceAll("\\s+", " ");
        boolean result = true;
        User currentUser = userRepository.findByEmail(principal.getName()).get();
        String photoPath = "";

        if (!authService.checkName(name)) {
            errors.put("name", "Имя указано не верно");
            result = false;
        }

        if (password != null && password.length() < AuthService.PASSWORD_LENGTH_THRESHOLD) {
            errors.put("password", "Пароль короче 6-ти символов");
            result = false;
        }

        if (!email.equals(currentUser.getEmail())) {
            Optional<User> userByEmail = userRepository.findByEmail(email);
            if (userByEmail.isPresent()) {
                errors.put("email", "Этот email уже зарегистрирован");
                result = false;
            }
        }

        if (multipartFile != null && removePhoto == 0) {
            ImageLoadResponse imageLoadResponse = fileService.loadImage(multipartFile, avatarsPath, true, AVATAR_CROP_SIZE);
            if (imageLoadResponse.isResult()) {
                photoPath = imageLoadResponse.getPath();
            } else {
                errors.put("photo", imageLoadResponse.getErrors().get("image"));
            }
        }

        if (result) {

            currentUser.setName(name);
            currentUser.setEmail(email);
            if (password != null) {
                currentUser.setPassword(passwordEncoder.encode(password));
            }
            if (removePhoto != null) {
                if (removePhoto == 0) {
                    currentUser.setPhoto(photoPath);
                } else if (removePhoto == 1) {
                    currentUser.setPhoto("");
                }
            }
            userRepository.save(currentUser);
            return new ProfileEditResponse(true);
        } else {
            return new ProfileEditResponse(false, errors);
        }


    }
}

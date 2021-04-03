package main.service;

import main.api.response.ProfileEditResponse;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final FileService fileService;

    @Value("${avatars.path}")
    private String avatarsPath;

    static int AVATAR_CROP_SIZE = 36;

    public ProfileService(final UserRepository userRepository,
                          final AuthService authService,
                          final FileService fileService) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.fileService = fileService;
    }

    public ProfileEditResponse ProfileEdit(final MultipartFile multipartFile,
                                           String name, final String email, final String password,
                                           final Integer removePhoto, final Principal principal) {
        HashMap<String, String> errors = new HashMap<>();
        name = name.trim().replaceAll("\\s+", " ");
        boolean result = true;

        String photoPath;

        if (!authService.checkName(name)) {
            errors.put("name", "Имя указано не верно");
            result = false;
        }

        if (password != null && password.length() < AuthService.PASSWORD_LENGTH_THRESHOLD) {
            errors.put("password", "Пароль короче 6-ти символов");
            result = false;
        }

        if (!multipartFile.isEmpty()) {
            photoPath = fileService.loadImage(multipartFile, avatarsPath, true, AVATAR_CROP_SIZE).getPath();
        }

        return new ProfileEditResponse(true);
    }
}

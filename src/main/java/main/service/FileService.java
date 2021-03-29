package main.service;

import main.api.response.ImageLoadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

@Service
public class FileService {
    private static final String JPEG_TYPE = "image/jpeg";
    private static final String PNG_TYPE = "image/png";
    private static final String JPEG_EXT = "jpeg";
    private static final String PNG_EXT = "png";
    private static final int FILE_SIZE_THRESHOLD = 2_097_152;

    @Value("${upload.path}")
    private String uploadPath;

    public ImageLoadResponse loadImage(final MultipartFile file) {
        String contentType = file.getContentType();

        if (contentType.equals(JPEG_TYPE)) {
            return checkSizeAndSaveImage(file, JPEG_EXT);
        } else if (contentType.equals(PNG_TYPE)) {
            return checkSizeAndSaveImage(file, PNG_EXT);
        }

        HashMap<String, String> errors = new HashMap<>();
        errors.put("image", "Файл не jpg/png");
        return new ImageLoadResponse(false, errors);
    }

    private ImageLoadResponse checkSizeAndSaveImage(final MultipartFile file, String fileExtension) {
        if (file.getSize() > FILE_SIZE_THRESHOLD) {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("image", "Картинка больше 2-х мегабайт");
            return new ImageLoadResponse(false, errors);
        }

        String randomUuid = UUID.randomUUID().toString();
        String currentUploadPath1 = uploadPath + "/" + randomUuid.substring(0, 2);
        String currentUploadPath2 = currentUploadPath1 + "/" + randomUuid.substring(2, 4);
        String currentUploadPath3 = currentUploadPath2 + "/" + randomUuid.substring(4, 6);

        Path filePath = Paths.get(currentUploadPath3
                + "/" + UUID.randomUUID().toString().substring(0, 8) + "." + fileExtension);
        try {
            createDir(Paths.get(currentUploadPath1));
            createDir(Paths.get(currentUploadPath2));
            createDir(Paths.get(currentUploadPath3));
            file.transferTo(filePath);
            return new ImageLoadResponse(filePath.toString(), true);
        } catch (IOException e) {
            e.printStackTrace();
            HashMap<String, String> errors = new HashMap<>();
            errors.put("image", "Ошибка сохранения файла");
            return new ImageLoadResponse(false, errors);
        }
    }

    private void createDir(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
    }
}

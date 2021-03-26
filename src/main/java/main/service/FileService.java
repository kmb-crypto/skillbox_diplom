package main.service;

import main.api.response.ImageLoadResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

@Service
public class FileService {
    private static final String JPEG_TYPE = "image/jpeg";
    private static final String PNG_TYPE = "image/png";
    private static final String JPEG_EXT = "jpeg";
    private static final String PNG_EXT = "png";

    public ImageLoadResponse loadImage(final MultipartFile file) {
        String contentType = file.getContentType();

        if (contentType.equals(JPEG_TYPE)) {
            return saveImage(file, JPEG_EXT);
        } else if (contentType.equals(PNG_TYPE)) {
            return saveImage(file, PNG_EXT);
        }

        HashMap<String, String> errors = new HashMap<>();
        errors.put("image", "Файл не jpg/png");
        return new ImageLoadResponse(false, errors);
    }

    private ImageLoadResponse saveImage(final MultipartFile file, String fileExtension) {
        Path path = Paths.get("./upload/image." + fileExtension);

        try {
            Files.createDirectory(Path.of("./upload"));
            file.transferTo(path);
            return new ImageLoadResponse(path.toString(), true);
        } catch (IOException e) {
            e.printStackTrace();
            HashMap<String, String> errors = new HashMap<>();
            errors.put("image", "Ошибка сохранения файла");
            return new ImageLoadResponse(false, errors);
        }
    }
}

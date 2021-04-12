package main.service;

import main.api.response.ImageLoadResponse;
import org.imgscalr.Scalr;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

@Service
public class FileService {
    private final Environment environment;

    private static final String JPEG_TYPE = "image/jpeg";
    private static final String PNG_TYPE = "image/png";
    private static final String JPEG_EXT = "jpeg";
    private static final String PNG_EXT = "png";
    private static final int FILE_SIZE_THRESHOLD = 2_097_152;

    public FileService(final Environment environment) {
        this.environment = environment;
    }

    public ImageLoadResponse loadImage(final MultipartFile file, final String uploadPath, final boolean isCrop, final Integer sizeAfterCrop) {
        String contentType = file.getContentType();
        HashMap<String, String> errors = new HashMap<>();
        errors.put("image", "Файл не jpg/png");
        return contentType.equals(JPEG_TYPE) ? checkSizeAndSaveImage(file, JPEG_EXT, uploadPath, isCrop, sizeAfterCrop) :
                (contentType.equals(PNG_TYPE) ? checkSizeAndSaveImage(file, PNG_EXT, uploadPath, isCrop, sizeAfterCrop) :
                        new ImageLoadResponse(false, errors));
    }


    ImageLoadResponse checkSizeAndSaveImage(final MultipartFile file, final String fileExtension,
                                            final String uploadPath, boolean isCrop, final Integer sizeAfterCrop) {
        if (file.getSize() > FILE_SIZE_THRESHOLD) {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("image", "Картинка больше 2-х мегабайт");
            return new ImageLoadResponse(false, errors);
        }

        String randomUuid = UUID.randomUUID().toString();
        String currentUploadPath1 = uploadPath + "/" + randomUuid.substring(0, 2);
        String currentUploadPath2 = currentUploadPath1 + "/" + randomUuid.substring(2, 4);
        String currentUploadPath3 = currentUploadPath2 + "/" + randomUuid.substring(4, 6);

        String fullFilePath = currentUploadPath3
                + "/" + UUID.randomUUID().toString().substring(0, 8) + "." + fileExtension;
        try {
            createDir(Paths.get(currentUploadPath1));
            createDir(Paths.get(currentUploadPath2));
            createDir(Paths.get(currentUploadPath3));

            if (isCrop) {
                BufferedImage image = Scalr.resize(ImageIO.read(file.getInputStream()), sizeAfterCrop, sizeAfterCrop);
                ImageIO.write(image, fileExtension, new File(fullFilePath));

            } else {
                Path filePath = Paths.get(fullFilePath);
                file.transferTo(filePath);

            }
            String responsePath = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/" + (fullFilePath).replace("\\", "/"))
                    .encode().build().toUri().toURL().toString();

            return new ImageLoadResponse(responsePath, true);
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

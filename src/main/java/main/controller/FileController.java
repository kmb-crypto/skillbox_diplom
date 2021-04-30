package main.controller;

import main.api.response.ImageLoadResponse;
import main.config.SpringConfig;
import main.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileController {

    private final FileService fileService;
    private final SpringConfig config;

    @Autowired
    public FileController(final FileService fileService, final SpringConfig config) {
        this.fileService = fileService;
        this.config = config;
    }

    @PostMapping(value = "/image")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity loadImage(@RequestParam("image") final MultipartFile file) {
        ImageLoadResponse imageLoadResponse = fileService.loadImage(file, config.getUploadPath(), false, null);

        if (imageLoadResponse.isResult()) {

            return ResponseEntity.status(HttpStatus.OK).body(imageLoadResponse.getPath());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(imageLoadResponse);
        }
    }
}

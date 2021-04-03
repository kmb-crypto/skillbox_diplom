package main.controller;

import main.api.request.ProfileEditRequest;
import main.api.response.ProfileEditResponse;
import main.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(final ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping(value = "/profile/my", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity editProfileMultipart(@RequestParam(value = "photo", required = false) final MultipartFile multipartFile,
                                               @RequestParam("name") final String name,
                                               @RequestParam("email") final String email,
                                               @RequestParam(value = "password", required = false) final String password,
                                               @RequestParam(value = "removePhoto", required = false) final int removePhoto,
                                               final Principal principal) {
        return new ResponseEntity(profileService.ProfileEdit(multipartFile, name, email, password, removePhoto, principal), HttpStatus.OK);

    }

    @PostMapping(value = "/profile/my", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity editProfileJson(@RequestBody ProfileEditRequest profileEditRequest,
                                          final Principal principal) {

        return new ResponseEntity(profileService.ProfileEdit(null,
                profileEditRequest.getName(),
                profileEditRequest.getEmail(),
                profileEditRequest.getPassword(),
                profileEditRequest.getRemovePhoto(),
                principal), HttpStatus.OK);
    }
}

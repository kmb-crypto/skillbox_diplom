package main.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class ProfileEditRequest {
    private String photo;
    private String name;
    private String email;
    private String password;
    private Integer removePhoto;
}

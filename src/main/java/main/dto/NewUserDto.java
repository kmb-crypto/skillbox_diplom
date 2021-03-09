package main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
public class NewUserDto {
    @JsonProperty(value = "e_mail")
    private String email;

    private String password;
    private String name;
    private String captcha;

    @JsonProperty(value = "captcha_secret")
    private String captchaSecret;
}

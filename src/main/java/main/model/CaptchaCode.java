package main.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "captcha_codes")
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaCode extends BaseEntity {

    @Column(nullable = false)
    private Timestamp time;

    @Column(nullable = false, columnDefinition = "TINYTEXT")
    private String code;

    @Column(name = "secret_code", nullable = false, columnDefinition = "TINYTEXT")
    private String secretCode;

}



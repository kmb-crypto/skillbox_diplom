package main.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
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



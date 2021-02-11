package main.model;

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
public class CaptchaCode extends BaseEntity {

    @Column(nullable = false)
    private Timestamp time;

    @Column(nullable = false)
    private short code;

    @Column(name = "secret_code", nullable = false)
    private short secretCode;

}



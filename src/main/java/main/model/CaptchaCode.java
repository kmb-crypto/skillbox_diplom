package main.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

public class CaptchaCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @Getter
    @Setter
    private int id;

    @Column(nullable = false)
    @Getter
    @Setter
    private Timestamp time;

    @Column(nullable = false)
    @Getter
    @Setter
    private short code;

    @Column(name = "secret_code", nullable = false)
    @Getter
    @Setter
    private short secretCode;

    public CaptchaCode() {
    }

}



package main.controller;

import main.api.response.CaptchaResponse;
import main.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CaptchaController {
    private final CaptchaService captchaService;

    @Autowired
    public CaptchaController(final CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @GetMapping(value = "/auth/captcha")
    public CaptchaResponse getCaptcha() {
        return captchaService.captchaGenerator();
    }
}

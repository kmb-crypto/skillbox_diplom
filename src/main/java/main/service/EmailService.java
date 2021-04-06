package main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    private final JavaMailSender emailSender;

    @Autowired
    public EmailService(final JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(final String to, final String subject, final String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("forrumdevpub@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

}

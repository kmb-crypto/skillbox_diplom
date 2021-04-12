package main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailService {

    private final JavaMailSender emailSender;

    @Autowired
    public EmailService(final JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(final String to, final String subject, final String text) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setFrom("forrumdevpub@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
        } catch (MessagingException e) {
            System.out.println("Проблемы с отправкой email со ссылкой восстановления пароля");
            e.printStackTrace();
        }
        emailSender.send(mimeMessage);
    }

}

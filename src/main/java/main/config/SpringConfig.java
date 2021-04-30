package main.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Properties;

@Configuration
public class SpringConfig implements WebMvcConfigurer {
    @Value("${upload.path:upload}")
    private String uploadPath;

    @Value("${avatars.path:upload_avatars}")
    private String avatarsPath;

    @Value("${blog.email.sender}")
    private String userNameEmail;

    @Value("${blog.email.sender.password}")
    private String emailPassword;

    @Value("${blog.email.sender.host}")
    private String host;

    @Value("${blog.email.sender.port}")
    private int port;

    @Value("${blog.email.sender.protocol}")
    private String protocol;

    @Value("${blog.email.sender.smtp.auth}")
    private String smtpAuth;

    @Value("${blog.email.sender.smtp.starttls.enable}")
    private String startTlsEnable;

    @Value("${blog.email.sender.debug}")
    private String emailDebug;

    @Bean
    public MultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    @Bean
    public JavaMailSender getJavaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(userNameEmail);
        mailSender.setPassword(emailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", startTlsEnable);
        props.put("mail.debug", emailDebug);

        return mailSender;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/" + uploadPath + "/**")
                .addResourceLocations("file:" + uploadPath + "/");

        registry.addResourceHandler("/" + avatarsPath + "/**")
                .addResourceLocations("file:" + avatarsPath + "/");

    }

    public String getUploadPath() {
        return uploadPath;
    }

    public String getAvatarsPath() {
        return avatarsPath;
    }
}

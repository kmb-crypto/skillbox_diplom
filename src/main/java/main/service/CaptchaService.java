package main.service;

import com.github.cage.Cage;
import main.api.response.CaptchaResponse;
import main.model.CaptchaCode;
import main.repository.CaptchaRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.GregorianCalendar;

@Service
public class CaptchaService {
    private final static String BASE64_DATA_FIRST_STRING = "data:image/png;base64, ";
    private final static int OLD_CAPTCHA_CHECH_SHEDULE = 120000;
    private final CaptchaRepository captchaRepository;

    @Value("${blog.captcha.lifetime.minutes:5}")
    private int captchaLifetimeMinutes;

    @Value("${blog.captcha.width:100}")
    private int captchaWidth;

    @Value("${blog.captcha.height:35}")
    private int captchaHeight;

    @Autowired
    public CaptchaService(final CaptchaRepository captchaRepository) {
        this.captchaRepository = captchaRepository;
    }

    public CaptchaResponse captchaGenerator() {
        Cage cage = new Cage(null, null, null, "png", null, null, null);
        String code = cage.getTokenGenerator().next();
        BufferedImage image = Scalr.resize(cage.drawImage(code), captchaWidth, captchaHeight);

        String secretCode = DigestUtils.sha256Hex(code);

        captchaRepository.save(new CaptchaCode(new Timestamp(System.currentTimeMillis()), code, secretCode));

        return new CaptchaResponse(secretCode,
                BASE64_DATA_FIRST_STRING + Base64.getEncoder().encodeToString(getByteData(image)));
    }

    private byte[] getByteData(final BufferedImage bufferedImage) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", baos);
        } catch (IOException e) {
            System.out.println("convertion bufferedImage -> byte[] problem");
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    @Scheduled(fixedDelay = OLD_CAPTCHA_CHECH_SHEDULE)
    public void removeOldCaptcha() {

        if (captchaRepository.removeOldCaptcha(captchaLifetimeMinutes) > 0) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            System.out.println("Old captcha removed : " + gregorianCalendar.getTime());
        }
    }
}

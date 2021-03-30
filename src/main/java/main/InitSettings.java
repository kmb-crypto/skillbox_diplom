package main;

import lombok.Getter;
import main.model.GlobalSetting;
import main.repository.GlobalSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;

@Component
public class InitSettings {

    @Value("${blog.multiuser.mode}")
    private boolean isMultiuserMode;

    @Value("${blog.post.premoderation}")
    private boolean isPostPremoderation;

    @Value("${blog.statistic.is.public}")
    private boolean isStatisticPublic;

    @Getter
    private static final String MULTI_USER_CODE = "MULTIUSER_MODE";
    private static final String MULTI_USER_NAME = "Многопользовательский режим";

    @Getter
    private static final String POST_PREMODERATION_CODE = "POST_PREMODERATION";
    private static final String POST_PREMODERATION_NAME = "Премодерация постов";

    @Getter
    private static final String STATISTIC_IS_PUBLIC_CODE = "STATISTIC_IS_PUBLIC";
    private static final String STATISTIC_IS_PUBLIC_NAME = "Показывать всем статистику блога";

    @Getter
    private static final String YES = "YES";

    @Getter
    private static final String NO = "NO";

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private GlobalSettingsRepository globalSettingsRepository;

    @PostConstruct
    private void postConstruct() {

        System.out.println("PostConstruct start");
        if (globalSettingsRepository.count() != 3) {

            GlobalSetting multiuser = new GlobalSetting();
            multiuser.setCode(MULTI_USER_CODE);
            multiuser.setName(MULTI_USER_NAME);
            multiuser.setValue(isMultiuserMode ? YES : NO);

            GlobalSetting postPremoderation = new GlobalSetting();
            postPremoderation.setCode(POST_PREMODERATION_CODE);
            postPremoderation.setName(POST_PREMODERATION_NAME);
            postPremoderation.setValue(isPostPremoderation ? YES : NO);

            GlobalSetting statisticIsPublic = new GlobalSetting();
            statisticIsPublic.setCode(STATISTIC_IS_PUBLIC_CODE);
            statisticIsPublic.setName(STATISTIC_IS_PUBLIC_NAME);
            statisticIsPublic.setValue(isStatisticPublic ? YES : NO);

            globalSettingsRepository.save(multiuser);
            globalSettingsRepository.save(postPremoderation);
            globalSettingsRepository.save(statisticIsPublic);

            System.out.println("Global settings initialized");
        } else {
            System.out.println("Global settings ok");
        }

        Path uploadImagePath = Paths.get(uploadPath).toAbsolutePath();
        if (!Files.exists(uploadImagePath)) {
            try {
                Files.createDirectory(uploadImagePath);
                System.out.println("Upload image folder created");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Can't create upload image folder");
            }
        }
    }


}

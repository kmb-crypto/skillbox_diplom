package main;

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

@Component
public class InitSettings {

    @Value("${blog.multiuser.mode}")
    private boolean isMultiuserMode;

    @Value("${blog.post.premoderation}")
    private boolean isPostPremoderation;

    @Value("${blog.statistic.is.public}")
    private boolean isStatisticPublic;

    public static final String MULTI_USER_CODE = "MULTIUSER_MODE";
    public static final String MULTI_USER_NAME = "Многопользовательский режим";

    public static final String POST_PREMODERATION_CODE = "POST_PREMODERATION";
    public static final String POST_PREMODERATION_NAME = "Премодерация постов";

    public static final String STATISTIC_IS_PUBLIC_CODE = "STATISTIC_IS_PUBLIC";
    public static final String STATISTIC_IS_PUBLIC_NAME = "Показывать всем статистику блога";

    public static final String YES = "YES";

    public static final String NO = "NO";

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${avatars.path}")
    private String avatarsPath;

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

        uploadFilesPathCreate(uploadPath);
        uploadFilesPathCreate(avatarsPath);
    }

    private void uploadFilesPathCreate(final String uploadPath) {
        Path uploadImagePath = Paths.get(uploadPath).toAbsolutePath();
        if (!Files.exists(uploadImagePath)) {
            try {
                Files.createDirectory(uploadImagePath);
                System.out.println(uploadPath + " folder created");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Can't create " + uploadPath + " folder");
            }
        }
    }
}

package main;

import main.model.GlobalSetting;
import main.repository.GlobalSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitSettings {
    private final String MULTI_USER_CODE = "MULTIUSER_MODE";
    private final String MULTI_USER_NAME = "Многопользовательский режим";

    private final String POST_PREMODERATION_CODE = "POST_PREMODERATION";
    private final String POST_PREMODERATION_NAME = "Премодерация постов";

    private final String STATISTIC_IS_PUBLIC_CODE = "STATISTIC_IS_PUBLIC";
    private final String STATISTIC_IS_PUBLIC_NAME = "Показывать всем статистику блога";

    private final String YES = "Yes";
    private final String NO = "NO";

    @Autowired
    private GlobalSettingsRepository globalSettingsRepository;

    @PostConstruct
    private void postConstruct() {

        System.out.println("PostConstruct start");
        if (globalSettingsRepository.count() != 3) {

            GlobalSetting multiuser = new GlobalSetting();
            multiuser.setCode(MULTI_USER_CODE);
            multiuser.setName(MULTI_USER_NAME);
            multiuser.setValue(NO);

            GlobalSetting postPremoderation = new GlobalSetting();
            postPremoderation.setCode(POST_PREMODERATION_CODE);
            postPremoderation.setName(POST_PREMODERATION_NAME);
            postPremoderation.setValue(YES);

            GlobalSetting statisticIsPublic = new GlobalSetting();
            statisticIsPublic.setCode(STATISTIC_IS_PUBLIC_CODE);
            statisticIsPublic.setName(STATISTIC_IS_PUBLIC_NAME);
            statisticIsPublic.setValue(NO);

            globalSettingsRepository.save(multiuser);
            globalSettingsRepository.save(postPremoderation);
            globalSettingsRepository.save(statisticIsPublic);

            System.out.println("Global settings initialized");
        } else {
            System.out.println("Global settings ok");
        }

    }

}

package main.service;

import main.InitSettings;
import main.api.request.SettingsRequest;
import main.api.response.SettingsResponse;
import main.model.GlobalSetting;
import main.repository.GlobalSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    private final GlobalSettingsRepository globalSettingsRepository;

    @Autowired
    public SettingsService(final GlobalSettingsRepository globalSettingsRepository) {
        this.globalSettingsRepository = globalSettingsRepository;
    }

    public SettingsResponse getGlobalSettings() {
        Iterable<GlobalSetting> settingIterable = globalSettingsRepository.findAll();
        SettingsResponse settingsResponse = new SettingsResponse();
        for (GlobalSetting setting : settingIterable) {
            String code = setting.getCode();
            if (code.equals(InitSettings.MULTI_USER_CODE)) {
                settingsResponse.setMultiuserMode(valueToBoolean(setting));
                continue;
            } else if (code.equals(InitSettings.POST_PREMODERATION_CODE)) {
                settingsResponse.setPostPremoderation(valueToBoolean(setting));
                continue;
            } else {
                settingsResponse.setStatisticIsPublic(valueToBoolean(setting));
            }
        }
        return settingsResponse;
    }

    public void setGlobalSettings(final SettingsRequest settingsRequest) {

        GlobalSetting multiuser = globalSettingsRepository.findByCode(InitSettings.MULTI_USER_CODE);
        multiuser.setValue(settingsRequest.isMultiuserMode() ? InitSettings.YES : InitSettings.NO);

        GlobalSetting postPremoderation = globalSettingsRepository.findByCode(InitSettings.POST_PREMODERATION_CODE);
        postPremoderation.setValue(settingsRequest.isPostPremoderation() ? InitSettings.YES : InitSettings.NO);

        GlobalSetting statisticIsPublic = globalSettingsRepository.findByCode(InitSettings.STATISTIC_IS_PUBLIC_CODE);
        statisticIsPublic.setValue(settingsRequest.isStatisticIsPublic() ? InitSettings.YES : InitSettings.NO);

        globalSettingsRepository.save(multiuser);
        globalSettingsRepository.save(postPremoderation);
        globalSettingsRepository.save(statisticIsPublic);
    }

    private boolean valueToBoolean(final GlobalSetting setting) {
        return setting.getValue().equals(InitSettings.YES);
    }


}

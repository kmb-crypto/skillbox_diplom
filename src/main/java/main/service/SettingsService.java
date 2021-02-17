package main.service;

import main.InitSettings;
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
            if (code.equals(InitSettings.getMULTI_USER_CODE())) {
                settingsResponse.setMultiuserMode(valueToBoolean(setting));
                //System.out.println(code + " " + setting.getValue());
                continue;
            } else if (code.equals(InitSettings.getPOST_PREMODERATION_CODE())) {
                settingsResponse.setPostPremoderation(valueToBoolean(setting));
                //System.out.println(code + " " + setting.getValue());
                continue;
            } else {
                settingsResponse.setStatisticIsPublic(valueToBoolean(setting));
                //System.out.println(code + " " + setting.getValue());
            }
        }
        return settingsResponse;
    }

    private boolean valueToBoolean(GlobalSetting setting) {
        if (setting.getValue().equals(InitSettings.getYES())) {

            return true;
        } else {
            return false;
        }
    }
}

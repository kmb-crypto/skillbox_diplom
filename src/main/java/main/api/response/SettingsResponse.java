package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class SettingsResponse {
    @JsonProperty("MULTIUSER_MODE")
    @Getter
    @Setter
    private boolean multiuserMode;

    @JsonProperty("POST_PREMODERATION")
    @Getter
    @Setter
    private boolean postPremoderation;

    @JsonProperty("STATISTICS_IS_PUBLIC")
    @Getter
    @Setter
    private boolean statisticIsPublic;

}

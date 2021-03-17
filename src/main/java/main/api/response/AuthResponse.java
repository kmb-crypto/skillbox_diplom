package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.dto.AuthResponseUserDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private boolean result;
    @JsonProperty("user")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AuthResponseUserDto authResponseUserDto;

}

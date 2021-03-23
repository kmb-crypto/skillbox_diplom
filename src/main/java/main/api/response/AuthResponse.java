package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import main.dto.AuthResponseUserDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private boolean result;
    @JsonProperty("user")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AuthResponseUserDto authResponseUserDto;

}

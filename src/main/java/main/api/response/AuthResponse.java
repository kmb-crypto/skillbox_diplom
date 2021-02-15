package main.api.response;

import lombok.Getter;
import lombok.Setter;
import main.dto.AuthResponseUserDto;

@Getter
@Setter
public class AuthResponse {

    private boolean result;
    private AuthResponseUserDto authResponseUserDto;

}

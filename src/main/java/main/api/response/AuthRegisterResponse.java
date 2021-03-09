package main.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRegisterResponse {
    private boolean result;
    private HashMap<String, String> errors;

    public AuthRegisterResponse(boolean result) {
        this.result = result;
    }
}

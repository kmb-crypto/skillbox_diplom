package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeResponse {
    private boolean result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HashMap<String, String> errors;

    public PasswordChangeResponse(boolean result) {
        this.result = result;
    }
}

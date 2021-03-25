package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashMap;

@Data
public class ImageLoadResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String path;

    private boolean result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HashMap<String, String> errors;

    public ImageLoadResponse(String path, boolean result) {
        this.path = path;
        this.result = result;
    }

    public ImageLoadResponse(boolean result, HashMap<String, String> errors) {
        this.result = result;
        this.errors = errors;
    }
}

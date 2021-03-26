package main.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashMap;

@Data
public class CommentResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;

    private boolean result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HashMap<String, String> errors;

    public CommentResponse(boolean result, Integer id) {
        this.result = result;
        this.id = id;
    }

    public CommentResponse(boolean result, HashMap<String, String> errors) {
        this.result = result;
        this.errors = errors;
    }

    public CommentResponse(boolean result) {
        this.result = result;
    }
}

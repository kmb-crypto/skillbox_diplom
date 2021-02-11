package main.api.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import main.dto.PostResponseDto;

import java.util.List;

public class PostsResponse {

    @Getter
    @Setter
    private int count;

    @Getter
    @Setter
    @JsonProperty("posts")
    private List<PostResponseDto> postIterable;

    public PostsResponse(int count, List<PostResponseDto> postIterable) {
        this.count = count;
        this.postIterable = postIterable;
    }
}

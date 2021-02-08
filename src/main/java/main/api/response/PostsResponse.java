package main.api.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import main.model.Post;

import java.util.HashMap;
import java.util.List;

public class PostsResponse {

    @Getter
    @Setter
    private int count;

    @Getter
    @Setter
    @JsonProperty("posts")
    private List<PostResponseObject> postIterable;

    public PostsResponse(int count, List<PostResponseObject> postIterable) {
        this.count = count;
        this.postIterable = postIterable;
    }
}

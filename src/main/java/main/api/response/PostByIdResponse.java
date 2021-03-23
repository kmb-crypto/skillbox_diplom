package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.dto.CommentsResponseDto;
import main.dto.PostsResponseUserDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostByIdResponse {
    private int id;
    private long timestamp;
    private boolean active;

    @JsonProperty("user")
    private PostsResponseUserDto userDto;

    private String title;
    private String text;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;

    private List<CommentsResponseDto> comments;

    private List<String> tags;

}

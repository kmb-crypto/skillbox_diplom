package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.dto.CommentsResponseDto;
import main.dto.PostsResponseUserDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostByIdResponse {
    private int id;
    private long timestamp;
    private boolean active;

    @JsonProperty("user")
    private PostsResponseUserDto userDto;

    private String title;
    private String announce;
    private int likeCount;
    private int dislikeCount;
    private int commentCount;
    private int viewCount;

    private List<CommentsResponseDto> comments;

}

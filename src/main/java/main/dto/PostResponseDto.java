package main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

public class PostResponseDto {
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private Timestamp time;

    @Getter
    @Setter
    @JsonProperty("user")
    private PostResponseUserDto userDto;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String announce;

    @Getter
    @Setter
    private int likeCount;

    @Getter
    @Setter
    private int dislikeCount;

    @Getter
    @Setter
    private int commentCount;

    @Getter
    @Setter
    private int viewCount;

}

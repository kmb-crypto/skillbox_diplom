package main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentsResponseDto {
    private int id;
    private long timestamp;
    private String text;

    @JsonProperty("user")
    private CommentsResponseUserDto commentsResponseUserDto;
}

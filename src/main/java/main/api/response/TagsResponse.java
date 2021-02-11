package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import main.dto.TagResponseDto;

import java.util.List;

public class TagsResponse {

    @Getter
    @Setter
    @JsonProperty("tags")
    List<TagResponseDto> tagsResponse;
}

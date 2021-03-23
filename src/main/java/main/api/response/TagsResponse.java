package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import main.dto.TagResponseDto;

import java.util.List;

@Data
@AllArgsConstructor
public class TagsResponse {


    @JsonProperty("tags")
    List<TagResponseDto> tagsResponse;
}

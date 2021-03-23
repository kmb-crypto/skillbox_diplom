package main.api.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import main.dto.PostsResponseDto;

import java.util.List;

@Data
@AllArgsConstructor
public class PostsResponse {

    private int count;

    @JsonProperty("posts")
    private List<PostsResponseDto> postResponseDtos;

}

//{
//        "count": 3,
//        "posts": [
//        {
//              "id": 31,
//              "timestamp": 1592338706,
//              "title": "Заголовок поста",
//              "announce": "Текст анонса (часть основного текста) поста без HTML-тэгов (HTML
//                          тэги необходимо удалить из текста анонса)", announce - предпросмотр поста, длина не более 150 символов, все HTML теги должны быть удалены, в
//                          конце полученной строки добавить троеточие ...
//              "likeCount": 36,
//              "dislikeCount": 3,
//              "commentCount": 15,
//              "viewCount": 55,
//              "user":
//              {
//                  "id": 88,
//                  "name": "Дмитрий Петров"
//              }
//        },
//        {...}
//        ]
//}

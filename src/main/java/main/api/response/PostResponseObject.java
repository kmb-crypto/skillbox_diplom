package main.api.response;

import lombok.Getter;
import lombok.Setter;
import main.model.User;

import java.sql.Timestamp;

public class PostResponseObject {
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private Timestamp time;

    @Getter
    @Setter
    private User user;

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

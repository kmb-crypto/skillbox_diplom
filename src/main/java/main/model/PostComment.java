package main.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "post_comments")
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @Getter
    @Setter
    private int id;

    @Column(name = "parent_id", nullable = false)
    @Getter
    @Setter
    private int parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @Getter
    @Setter
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Getter
    @Setter
    private User user;

    @Column(nullable = false)
    @Getter
    @Setter
    private Timestamp time;

    @Column(columnDefinition = "TEXT", nullable = false)
    @Getter
    @Setter
    private String text;

    public PostComment() {
    }


}

package main.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "post_comments")
@Getter
@Setter
@NoArgsConstructor
public class PostComment extends BaseEntity {

    @Column(name = "parent_id", nullable = false)
    private int parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Timestamp time;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;


}

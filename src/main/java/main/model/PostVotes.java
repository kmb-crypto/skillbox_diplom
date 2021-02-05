package main.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="post_votes")
public class PostVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @Getter
    @Setter
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Getter
    @Setter
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @Getter
    @Setter
    private Post post;

    @Column(nullable = false)
    @Getter
    @Setter
    private Timestamp time;

    @Column(nullable = false)
    @Getter
    @Setter
    private byte value;

    public PostVotes() {
    }


}

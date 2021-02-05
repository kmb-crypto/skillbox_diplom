package main.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @Getter
    @Setter
    private int id;

    @Column(name = "is_active", nullable = false)
    @Getter
    @Setter
    private byte isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status", nullable = false, columnDefinition = "enum('NEW', 'ACCEPTED', 'DECLINED')")
    @Getter
    @Setter
    private ModerationStatus moderationStatus;

    @Column(name = "moderator_id")
    @Getter
    @Setter
    private int moderatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Getter
    @Setter
    private User user;

    @Column(nullable = false)
    @Getter
    @Setter
    private Timestamp time;

    @Column(nullable = false)
    @Getter
    @Setter
    private String title;

    @Column(name = "view_count", nullable = false)
    @Getter
    @Setter
    private int viewCount;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @Setter
    private Set<PostComment> postComments;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @Setter
    private Set<PostVotes> postVotes;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tag2post",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    @Getter
    @Setter
    private Set<Tag> tags;

    public Post() {
    }


}

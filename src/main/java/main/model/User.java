package main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @Getter
    @Setter
    private int id;

    @Column(name = "is_moderator", nullable = false)
    @Getter
    @Setter
    @JsonIgnore
    private byte isModerator;

    @Column(name = "reg_time", nullable = false)
    @Getter
    @Setter
    @JsonIgnore
    private Timestamp regTime;

    @Column(nullable = false)
    @Getter
    @Setter
    private String name;

    @Column(nullable = false)
    @Getter
    @Setter
    @JsonIgnore
    private String email;

    @Column(nullable = false)
    @Getter
    @Setter
    @JsonIgnore
    private String password;  //hash of password

    @Getter
    @Setter
    @JsonIgnore
    private String code;

    @Column(columnDefinition = "TEXT")
    @Getter
    @Setter
    @JsonIgnore
    private String photo;  //link to photo

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @Setter
    @JsonIgnore
    private Set<Post> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Getter
    @Setter
    @JsonIgnore
    private Set<PostComment> postComments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Getter
    @Setter
    @JsonIgnore
    private Set<PostVotes> postVotes;

    public User() {
    }


}

package main.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tag2post")
public class Tag2Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @Getter
    @Setter
    private int id;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @Getter
    @Setter
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    @Getter
    @Setter
    private Tag tag;

    public Tag2Post() {
    }
}

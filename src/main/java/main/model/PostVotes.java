package main.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "post_votes")
@Getter
@Setter
@NoArgsConstructor
public class PostVotes extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private Timestamp time;

    @Column(nullable = false)
    private byte value;

}

//    id INT NOT NULL AUTO_INCREMENT    id лайка/дизлайка
//    user_id INT NOT NULL              тот, кто поставил лайк / дизлайк
//    post_id INT NOT NULL              пост, которому поставлен лайк / дизлайк
//    time DATETIME NOT NULL            дата и время лайка / дизлайка
//    value TINYINT NOT NULL            лайк или дизлайк: 1 или -1
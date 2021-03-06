package main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "post_votes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostVote extends BaseEntity {

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
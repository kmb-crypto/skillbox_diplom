package main.repository;

import main.dto.StatisticNative;
import main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT " +
            "(SELECT count(*)  FROM posts WHERE user_id = :id) AS postsCount, " +
            "(SELECT count(*) FROM posts " +
            "JOIN post_votes ON post_id = posts.id " +
            "WHERE posts.user_id = :id AND post_votes.value = 1) AS likesCount, " +
            "(SELECT count(*) FROM posts " +
            "JOIN post_votes ON post_id = posts.id " +
            "WHERE posts.user_id = :id AND post_votes.value = -1) AS dislikesCount, " +
            "(SELECT sum(view_count) FROM posts WHERE user_id = :id) AS viewsCount, " +
            "(SELECT MIN(time) FROM posts WHERE user_id = :id) AS firstPublication", nativeQuery = true)
    StatisticNative getMyStatistic(@Param("id") int id);
}

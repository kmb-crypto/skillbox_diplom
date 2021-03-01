package main.repository;

import main.dto.AmountOfPostsByDay;
import main.dto.CalendarYearNative;
import main.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    @Query(value = "SELECT * FROM posts " +
            "WHERE is_active = 1 AND time < now() AND moderation_status = 'ACCEPTED'",
            nativeQuery = true)
    List<Post> findAllPosts(Pageable pageable);

    @Query(value = "SELECT * FROM posts " +
            "WHERE is_active = 1 AND time < now() AND moderation_status = 'ACCEPTED' " +
            "ORDER BY (SELECT count(*) FROM post_votes " +
            "WHERE posts.id = post_votes.post_id AND post_votes.value = 1) DESC",
            nativeQuery = true)
    List<Post> findBestPosts(Pageable pageable);

    @Query(value = "SELECT * FROM posts " +
            "WHERE is_active = 1 AND time < now() AND moderation_status = 'ACCEPTED' " +
            "ORDER BY (SELECT count(*) FROM post_comments WHERE posts.id = post_comments.post_id) DESC",
            nativeQuery = true)
    List<Post> findPopularPosts(Pageable pageable);

    @Query(value = "SELECT count(*) FROM posts " +
            "WHERE is_active = 1 AND time < now() AND moderation_status = 'ACCEPTED'",
            nativeQuery = true)
    Integer countAllAvailablePosts();

    @Query(value = "SELECT DISTINCT EXTRACT(YEAR FROM posts.time) AS year FROM posts", nativeQuery = true)
    Set<CalendarYearNative> getCalendarYears();

    @Query(value = "SELECT DATE_FORMAT(posts.time, '%Y-%m-%d') AS date," +
            "COUNT(*) AS amount " +
            "FROM posts " +
            "WHERE is_active = 1 AND time<now() AND moderation_status='ACCEPTED' " +
            "AND EXTRACT(YEAR FROM posts.time) = :year " +
            "GROUP BY CAST(posts.time AS date)"
            ,nativeQuery = true)
    List<AmountOfPostsByDay> getAmountOfPostsByDay(@Param("year") int year);

}

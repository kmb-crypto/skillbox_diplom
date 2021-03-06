package main.repository;

import main.dto.AmountOfPostsByDayNative;
import main.dto.CalendarYearNative;
import main.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
            "WHERE is_active = 1 AND time < now() AND moderation_status='ACCEPTED' " +
            "AND EXTRACT(YEAR FROM posts.time) = :year " +
            "GROUP BY CAST(posts.time AS date)",
            nativeQuery = true)
    List<AmountOfPostsByDayNative> getAmountOfPostsByDay(@Param("year") int year);

    @Query(value = "SELECT * FROM posts " +
            "WHERE is_active = 1 AND time < now() AND moderation_status = 'ACCEPTED' " +
            "AND CAST(posts.time AS date) = :date",
            nativeQuery = true)
    List<Post> findAllPostsByDate(@Param("date") String date, Pageable pageable);

    @Query(value = "SELECT count(*) FROM posts " +
            "WHERE is_active = 1 AND time < now() AND moderation_status = 'ACCEPTED' " +
            "AND CAST(posts.time AS date) = :date",
            nativeQuery = true)
    Integer countAllAvailablePostsByDate(@Param("date") String date);

    @Query(value = "SELECT * FROM posts " +
            "WHERE is_active = 1 AND time < now() AND moderation_status = 'ACCEPTED' " +
            "AND (posts.text LIKE %:query% OR posts.title LIKE %:query%)", nativeQuery = true)
    List<Post> findAllPostsByQuery(@Param("query") String query, Pageable pageable);

    @Query(value = "SELECT count(*) FROM posts " +
            "WHERE is_active = 1 AND time < now() AND moderation_status='ACCEPTED' " +
            "AND (posts.text LIKE %:query% OR posts.title LIKE %:query%)", nativeQuery = true)
    Integer countAllAvailablePostsByQuery(@Param("query") String query);

    @Query(value = "SELECT * FROM posts " +
            "JOIN tag2post ON tag2post.post_id = posts.id " +
            "JOIN tags ON tags.id = tag2post.tag_id " +
            "WHERE  is_active = 1 AND time < now() AND moderation_status='ACCEPTED' AND " +
            "tags.name LIKE :tag", nativeQuery = true)
    List<Post> findAllPostsByTag(@Param("tag") String tag, Pageable pageable);

    @Query(value = "SELECT count(*) FROM posts " +
            "JOIN tag2post ON tag2post.post_id = posts.id " +
            "JOIN tags ON tags.id = tag2post.tag_id " +
            "WHERE  is_active = 1 AND time < now() AND moderation_status='ACCEPTED' AND " +
            "tags.name LIKE :tag", nativeQuery = true)
    Integer countAllAvailablePostsByTag(@Param("tag") String tag);

    Post findPostById(@Param("id") int id);

    @Query(value = "SELECT * FROM posts " +
            "JOIN users ON users.id = user_id " +
            "WHERE users.email = :email AND is_active = 0", nativeQuery = true)
    List<Post> findMyInactivePosts(@Param("email") String email, Pageable pageable);

    @Query(value = "SELECT count(*) FROM posts " +
            "JOIN users ON users.id = user_id " +
            "WHERE users.email = :email AND is_active = 0", nativeQuery = true)
    Integer countMyInactivePosts(@Param("email") String email);

    @Query(value = "SELECT * FROM posts " +
            "JOIN users ON users.id = user_id " +
            "WHERE users.email = :email AND is_active = 1 AND moderation_status = 'NEW'", nativeQuery = true)
    List<Post> findMyPendingPosts(@Param("email") String email, Pageable pageable);

    @Query(value = "SELECT count(*) FROM posts " +
            "JOIN users ON users.id = user_id " +
            "WHERE users.email = :email AND is_active = 1 AND moderation_status = 'NEW'", nativeQuery = true)
    Integer countMyPendingPosts(@Param("email") String email);

    @Query(value = "SELECT * FROM posts " +
            "JOIN users ON users.id = user_id " +
            "WHERE users.email = :email AND is_active = 1 AND moderation_status = 'DECLINED'", nativeQuery = true)
    List<Post> findMyDeclinedPosts(@Param("email") String email, Pageable pageable);

    @Query(value = "SELECT count(*) FROM posts " +
            "JOIN users ON users.id = user_id " +
            "WHERE users.email = :email AND is_active = 1 AND moderation_status = 'DECLINED'", nativeQuery = true)
    Integer countMyDeclinedPosts(@Param("email") String email);

    @Query(value = "SELECT * FROM posts " +
            "JOIN users ON users.id = user_id " +
            "WHERE users.email = :email AND is_active = 1 AND moderation_status = 'ACCEPTED'", nativeQuery = true)
    List<Post> findMyPublishedPosts(@Param("email") String email, Pageable pageable);

    @Query(value = "SELECT count(*) FROM posts " +
            "JOIN users ON users.id = user_id " +
            "WHERE users.email = :email AND is_active = 1 AND moderation_status = 'ACCEPTED'", nativeQuery = true)
    Integer countMyPublishedPosts(@Param("email") String email);

    @Query(value = "SELECT * FROM posts " +
            "WHERE moderation_status = 'NEW' AND is_active = 1 AND moderator_id IS null", nativeQuery = true)
    List<Post> findAllNewPosts(Pageable pageable);

    @Query(value = "SELECT count(*) FROM posts " +
            "WHERE moderation_status = 'NEW' AND is_active = 1 AND moderator_id IS null", nativeQuery = true)
    Integer countAllNewPosts();

    @Query(value = "SELECT * FROM posts " +
            "JOIN users ON users.id = moderator_id " +
            "WHERE moderation_status = 'DECLINED' AND users.email = :email", nativeQuery = true)
    List<Post> findDeclinedPostsByMe(@Param("email") String email, Pageable pageable);

    @Query(value = "SELECT count(*) FROM posts " +
            "JOIN users ON users.id = moderator_id " +
            "WHERE moderation_status = 'DECLINED' AND users.email = :email", nativeQuery = true)
    Integer countDeclinedPostsByMe(@Param("email") String email);

    @Query(value = "SELECT * FROM posts " +
            "JOIN users ON users.id = moderator_id " +
            "WHERE moderation_status = 'ACCEPTED' AND users.email = :email", nativeQuery = true)
    List<Post> findAcceptedPostsByMe(@Param("email") String email, Pageable pageable);

    @Query(value = "SELECT count(*) FROM posts " +
            "JOIN users ON users.id = moderator_id " +
            "WHERE moderation_status = 'ACCEPTED' AND users.email = :email", nativeQuery = true)
    Integer countAcceptedPostsByMe(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE posts SET posts.view_count = posts.view_count + 1 WHERE posts.id = :id",
            nativeQuery = true)
    void updateViewCount(@Param("id") int id);

}

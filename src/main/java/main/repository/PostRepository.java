package main.repository;

import main.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}

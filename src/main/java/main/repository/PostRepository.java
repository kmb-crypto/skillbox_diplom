package main.repository;

import main.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    @Query(value = "SELECT * FROM forrum.posts WHERE is_active = 1 AND time<now()",
            nativeQuery = true)
    List<Post> findAllPosts(Pageable pageable);
//    @Query("SELECT p FROM forrum.posts p WHERE p.is_active = 1 AND p.time<now()")
//    List<Post> findAllPosts(Pageable pageable);
}

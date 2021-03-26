package main.repository;

import main.model.PostComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<PostComment, Integer> {
    @Query(value = "SELECT count(*) FROM post_comments WHERE post_comments.post_id = :id",
            nativeQuery = true)
    Integer countComments(@Param("id") int id);

    PostComment findPostCommentById(@Param("id") int id);
}

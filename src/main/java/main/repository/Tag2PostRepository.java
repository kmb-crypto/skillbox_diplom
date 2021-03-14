package main.repository;

import main.model.Tag2Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Tag2PostRepository extends CrudRepository<Tag2Post, Integer> {

    @Query(value = "SELECT  count(*) FROM tag2post WHERE tag2post.tag_id= :id", nativeQuery = true)
    Integer countPostsForTag(@Param("id") int id);
}

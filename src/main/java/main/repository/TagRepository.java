package main.repository;

import main.dto.TagNative;
import main.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {

    @Query(value = "SELECT tags.name AS name, " +
            "count(*) / " +
            "(select count(*) from posts where is_active = 1 AND time < now() AND moderation_status='ACCEPTED') as weight " +
            "FROM tags " +
            "JOIN tag2post ON tag2post.tag_id = tags.id " +
            "JOIN posts ON posts.id = tag2post.post_id " +
            "WHERE posts.is_active = 1 " +
            "GROUP BY tags.id", nativeQuery = true)
    List<TagNative> getTagsWithWeights();


    @Query(value = "SELECT tags.name AS name, " +
            "count(*) / " +
            "(select count(*) from posts where is_active = 1 AND time < now() AND moderation_status='ACCEPTED') as weight " +
            "FROM tags " +
            "JOIN tag2post ON tag2post.tag_id = tags.id " +
            "JOIN posts ON posts.id = tag2post.post_id " +
            "WHERE posts.is_active = 1 AND tags.name LIKE :query " +
            "GROUP BY tags.id", nativeQuery = true)
    List<TagNative> getQueryTagsWithWeights(@Param("query") String query);
}

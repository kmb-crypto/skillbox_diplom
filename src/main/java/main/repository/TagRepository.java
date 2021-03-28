package main.repository;

import main.dto.TagNative;
import main.model.Tag;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {

    @Query(value = "SELECT tags.name AS name, " +
            "count(*) / " +
            "(select count(*) from posts where is_active = 1 AND time < now() AND moderation_status='ACCEPTED') as weight " +
            "FROM tags " +
            "JOIN tag2post ON tag2post.tag_id = tags.id " +
            "JOIN posts ON posts.id = tag2post.post_id " +
            "WHERE posts.is_active = 1 AND time < now() AND moderation_status = 'ACCEPTED' " +
            "GROUP BY tags.id", nativeQuery = true)
    List<TagNative> getTagsWithWeights();


    @Query(value = "SELECT tags.name AS name, " +
            "count(*) / " +
            "(select count(*) from posts where is_active = 1 AND time < now() AND moderation_status='ACCEPTED') as weight " +
            "FROM tags " +
            "JOIN tag2post ON tag2post.tag_id = tags.id " +
            "JOIN posts ON posts.id = tag2post.post_id " +
            "WHERE posts.is_active = 1 AND AND time < now() AND moderation_status = 'ACCEPTED' AND tags.name LIKE %:query% " +
            "GROUP BY tags.id", nativeQuery = true)
    List<TagNative> getQueryTagsWithWeights(@Param("query") String query);

    List<Tag> findTagsIdByNameIn(List<String> tags);

    @Transactional
    @Modifying
    @Query(value = "INSERT IGNORE INTO tags(name) VALUES (?1)", nativeQuery = true)
    void saveIgnoreDuplicateKey(String name);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM tags " +
            "WHERE tags.id > 0 AND tags.id NOT IN (SELECT tag_id FROM tag2post)", nativeQuery = true)
    int deleteOrphanTags();
}

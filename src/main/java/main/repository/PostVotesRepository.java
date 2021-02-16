package main.repository;

import main.model.PostVote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostVotesRepository extends CrudRepository<PostVote, Integer> {

    @Query(value = "SELECT count(*) FROM post_votes WHERE post_votes.post_id = :id AND post_votes.value = 1",
            nativeQuery = true)
    Integer countLikes(@Param("id") int id);

    @Query(value = "SELECT count(*) FROM post_votes WHERE post_votes.post_id = :id AND post_votes.value = -1",
            nativeQuery = true)
    Integer countDislikes(@Param("id") int id);
}

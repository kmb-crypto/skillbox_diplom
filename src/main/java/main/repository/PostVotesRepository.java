package main.repository;

import main.model.PostVote;
import org.springframework.data.repository.CrudRepository;

public interface PostVotesRepository extends CrudRepository<PostVote,Integer> {
}

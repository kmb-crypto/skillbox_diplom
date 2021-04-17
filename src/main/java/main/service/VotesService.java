package main.service;

import main.api.request.VoteRequest;
import main.api.response.VoteResponse;
import main.model.Post;
import main.model.PostVote;
import main.model.User;
import main.repository.PostRepository;
import main.repository.PostVotesRepository;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class VotesService {
    private final PostVotesRepository postVotesRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public VotesService(final PostVotesRepository postVotesRepository,
                        final UserRepository userRepository,
                        final PostRepository postRepository) {
        this.postVotesRepository = postVotesRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public VoteResponse getVoteResponse(final VoteRequest voteRequest, final Principal principal, final byte value) {
        if (principal==null){
            return new VoteResponse(false);
        }
        User currentUser = userRepository.findByEmail(principal.getName()).get();
        int currentUserId = currentUser.getId();
        int postId = voteRequest.getPostId();
        PostVote postVote =
                postVotesRepository.findPostVoteByUserIdAndPostId(currentUserId, postId);
        if (postVote != null && postVote.getValue() == value) {
            return new VoteResponse(false);
        }
        setVote(currentUser, postRepository.findPostById(postId), postVote, value);
        return new VoteResponse(true);
    }

    private void setVote(final User user, final Post post, final PostVote postVote, final byte value) {
        if (postVote != null) {
            postVotesRepository.delete(postVote);
        }
        PostVote newPostVote = new PostVote(user, post, Timestamp.from(Instant.now()), value);
        postVotesRepository.save(newPostVote);
    }
}

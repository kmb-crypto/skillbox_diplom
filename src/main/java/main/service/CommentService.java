package main.service;

import main.api.request.CommentRequest;
import main.api.response.CommentResponse;
import main.model.Post;
import main.model.PostComment;
import main.repository.CommentRepository;
import main.repository.PostRepository;
import main.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.HashMap;

@Service
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentService(final PostRepository postRepository,
                          final UserRepository userRepository,
                          final CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public CommentResponse addComment(final CommentRequest commentRequest, final Principal principal) {
        Integer parentId = commentRequest.getParentId();
        Post currentPost = postRepository.findPostById(commentRequest.getPostId());

        HashMap<String, String> errors = new HashMap<>();

        if (parentId == null && currentPost == null) {
            errors.put("text", "Неверный post_id");
            return new CommentResponse(false, errors);
        }

        if (parentId != null) {
            PostComment parentComment = commentRepository.findPostCommentById(parentId);
            if (currentPost == null && parentComment == null) {
                errors.put("text", "Неверный post_id и parent_id");
                return new CommentResponse(false, errors);
            } else if (parentComment == null) {
                errors.put("text", "Неверный parent_id");
                return new CommentResponse(false, errors);
            } else if (currentPost == null) {
                errors.put("text", "Неверный post_id");
                return new CommentResponse(false, errors);
            }
        }

        String text = commentRequest.getText();

        if (text.length() < 2) {
            errors.put("text", "Текст не задан или слишком короткий");
            return new CommentResponse(false, errors);
        }

        return new CommentResponse(saveComment(text, parentId, currentPost, principal));
    }
    private Integer saveComment(final String text, final Integer parentId, final Post post, final Principal principal) {
        PostComment postComment = new PostComment();

        postComment.setParentId(parentId);
        postComment.setPost(post);
        postComment.setUser(userRepository.findByEmail(principal.getName()).get());
        postComment.setTime(new Timestamp(System.currentTimeMillis()));
        postComment.setText(text);
        commentRepository.save(postComment);
        return postComment.getId();
    }

}

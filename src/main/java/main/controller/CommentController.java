package main.controller;

import main.api.request.CommentRequest;
import main.api.response.CommentResponse;
import main.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final PostService postService;

    public CommentController(final PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/comment")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity createComment(@RequestBody final CommentRequest commentRequest, final Principal principal) {
        CommentResponse commentResponse = postService.addComment(commentRequest, principal);

        if (commentResponse.getResult() != null && !commentResponse.getResult()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commentResponse);
        }

        return ResponseEntity.status(HttpStatus.OK).body(commentResponse);
    }
}

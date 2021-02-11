package main.controller;

import main.api.response.PostsResponse;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ApiPostController {

    private PostService postService;

    @Autowired
    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/post",
            params = {"offset", "limit", "mode"})
    private ResponseEntity getPosts(
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit,
            @RequestParam("mode") String mode) {
        //System.out.println("offset " + offset + ", limit " + limit + ", mode " + mode);
        PostsResponse postsResponse = postService.getPosts();

        if (postsResponse.getCount() == 0) {
            return new ResponseEntity(postsResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity(postsResponse, HttpStatus.OK);
        }
    }
}

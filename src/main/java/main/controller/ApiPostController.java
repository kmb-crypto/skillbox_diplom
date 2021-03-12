package main.controller;

import main.api.response.PostByIdResponse;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api")
public class ApiPostController {

    private final PostService postService;

    @Autowired
    public ApiPostController(final PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/post")
    private ResponseEntity getPosts(
            @RequestParam(value = "offset", defaultValue = "0") final Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") final Integer limit,
            @RequestParam(value = "mode", defaultValue = "recent") final String mode) {

        return new ResponseEntity(postService.getPostsResponse(offset, limit, mode), HttpStatus.OK);
    }

    @GetMapping(value = "/post/byDate")
    private ResponseEntity getPostsByDate(@RequestParam(value = "offset", defaultValue = "0") final Integer offset,
                                          @RequestParam(value = "limit", defaultValue = "10") final Integer limit,
                                          @RequestParam("date") final String date) {
        return new ResponseEntity(postService.getPostsByDateResponse(offset, limit, date), HttpStatus.OK);
    }

    @GetMapping(value = "/post/search")
    private ResponseEntity getPostsBySearch(@RequestParam(value = "offset", defaultValue = "0") final Integer offset,
                                            @RequestParam(value = "limit", defaultValue = "10") final Integer limit,
                                            @RequestParam("query") final String query) {
        return new ResponseEntity(postService.getPostsByQueryResponse(offset, limit, query), HttpStatus.OK);

    }

    @GetMapping(value = "/post/byTag")
    private ResponseEntity getPostsByTag(@RequestParam(value = "offset", defaultValue = "0") final int offset,
                                         @RequestParam(value = "limit", defaultValue = "10") final int limit,
                                         @RequestParam("tag") final String tag) {
        return new ResponseEntity(postService.getPostsByTagResponse(offset, limit, tag), HttpStatus.OK);
    }

    @GetMapping(value = "/post/{id}")
    private ResponseEntity getPostById(@PathVariable final int id) {

        Optional<PostByIdResponse> optionalPostByIdResponse = Optional.ofNullable(postService.getPostById(id));
        if (optionalPostByIdResponse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return new ResponseEntity(optionalPostByIdResponse.get(), HttpStatus.OK);
        }
    }
}
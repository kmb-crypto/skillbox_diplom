package main.controller;

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

    private final PostService postService;

    @Autowired
    public ApiPostController(final PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/post",
            params = {"offset", "limit", "mode"})
    private ResponseEntity getPosts(
            @RequestParam("offset") final int offset,
            @RequestParam("limit") final int limit,
            @RequestParam("mode") final String mode) {

        return new ResponseEntity(postService.getPosts(offset, limit, mode), HttpStatus.OK);

    }
}
package main.controller;

import main.api.request.PostRequest;
import main.api.response.ImageLoadResponse;
import main.api.response.PostByIdResponse;
import main.api.response.PostProcessingResponse;
import main.service.FileService;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;
    private final FileService fileService;

    @Autowired
    public PostController(final PostService postService, final FileService fileService) {
        this.postService = postService;
        this.fileService = fileService;
    }

    @GetMapping(value = "/post")
    public ResponseEntity getPosts(
            @RequestParam(value = "offset", defaultValue = "0") final Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") final Integer limit,
            @RequestParam(value = "mode", defaultValue = "recent") final String mode) {

        return new ResponseEntity(postService.getPostsResponse(offset, limit, mode), HttpStatus.OK);
    }

    @GetMapping(value = "/post/byDate")
    public ResponseEntity getPostsByDate(@RequestParam(value = "offset", defaultValue = "0") final Integer offset,
                                         @RequestParam(value = "limit", defaultValue = "10") final Integer limit,
                                         @RequestParam("date") final String date) {
        return new ResponseEntity(postService.getPostsByDateResponse(offset, limit, date), HttpStatus.OK);
    }

    @GetMapping(value = "/post/search")
    public ResponseEntity getPostsBySearch(@RequestParam(value = "offset", defaultValue = "0") final Integer offset,
                                           @RequestParam(value = "limit", defaultValue = "10") final Integer limit,
                                           @RequestParam("query") final String query) {
        return new ResponseEntity(postService.getPostsByQueryResponse(offset, limit, query), HttpStatus.OK);

    }

    @GetMapping(value = "/post/byTag")
    public ResponseEntity getPostsByTag(@RequestParam(value = "offset", defaultValue = "0") final int offset,
                                        @RequestParam(value = "limit", defaultValue = "10") final int limit,
                                        @RequestParam("tag") final String tag) {
        return new ResponseEntity(postService.getPostsByTagResponse(offset, limit, tag), HttpStatus.OK);
    }

    @GetMapping(value = "/post/{id}")
    public ResponseEntity getPostById(@PathVariable final int id) {

        Optional<PostByIdResponse> optionalPostByIdResponse = Optional.ofNullable(postService.getPostById(id));
        if (optionalPostByIdResponse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return new ResponseEntity(optionalPostByIdResponse.get(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/post/my")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity getMyPosts(@RequestParam(value = "offset", defaultValue = "0") final int offset,
                                     @RequestParam(value = "limit", defaultValue = "10") final int limit,
                                     @RequestParam(value = "status", defaultValue = "published") final String status,
                                     final Principal principal) {
        return new ResponseEntity(postService.getMyPosts(offset, limit, status, principal), HttpStatus.OK);

    }

    @GetMapping(value = "/post/moderation")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity getModerationPosts(@RequestParam(value = "offset", defaultValue = "0") final int offset,
                                             @RequestParam(value = "limit", defaultValue = "10") final int limit,
                                             @RequestParam(value = "status", defaultValue = "new") final String status,
                                             final Principal principal) {
        return new ResponseEntity(postService.getModerationPosts(offset, limit, status, principal), HttpStatus.OK);
    }

    @PostMapping(value = "/post")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity createPost(@RequestBody final PostRequest postRequest, final Principal principal) {
        return new ResponseEntity(postService.createPost(postRequest, principal), HttpStatus.OK);
    }

//    @PutMapping(value ="/post/{ID}")
//    public ResponseEntity editPostById (@PathVariable final int id, @RequestBody PostRequest postRequest){
//
//    }

    @PostMapping(value = "/image")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity loadImage(@RequestParam("image") final MultipartFile file) {
        ImageLoadResponse imageLoadResponse = fileService.loadImage(file);

        if (imageLoadResponse.isResult()) {
            return ResponseEntity.status(HttpStatus.OK).body(imageLoadResponse.getPath());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(imageLoadResponse);
        }
    }


}
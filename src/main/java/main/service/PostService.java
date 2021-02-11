package main.service;

import main.api.response.PostsResponse;
import main.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostsResponse getPosts() {

        if (postRepository.count() == 0) {
            return new PostsResponse(0, new ArrayList<>());
        } else {
//            Iterable<Post> postIterable = postRepository.findAll();
//            HashMap<Integer, Post> posts = new HashMap<>();
            return new PostsResponse(0, new ArrayList<>());
        }
    }

}




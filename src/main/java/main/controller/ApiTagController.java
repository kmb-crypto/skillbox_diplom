package main.controller;

import main.api.response.TagsResponse;
import main.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class ApiTagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tag")
    private TagsResponse tagResponse(){
        return tagService.tagResponse();
    }
}

package main.controller;

import main.api.response.TagsResponse;
import main.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiTagController {

    private final TagService tagService;

    @Autowired
    public ApiTagController(final TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/tag")
    private TagsResponse tagResponse(@RequestParam(value = "query", required = false) final String query) {

        return tagService.tagResponse(query);
    }
}

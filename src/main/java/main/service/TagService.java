package main.service;

import main.api.response.TagsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TagService {


    public TagsResponse tagResponse(){
        TagsResponse tagsResponse = new TagsResponse();
        tagsResponse.setTagsResponse(new ArrayList<>());
        return tagsResponse;
    }

}

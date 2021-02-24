package main.service;

import main.api.response.TagsResponse;
import main.dto.TagResponseDto;
import main.dto.TagResponseNative;
import main.repository.PostRepository;
import main.repository.Tag2PostRepository;
import main.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final PostRepository postRepository;
    private final Tag2PostRepository tag2PostRepository;
    private static final float WEIGHT_THRESHOLD = 0.3f;

    @Autowired
    public TagService(final TagRepository tagRepository,
                      final PostRepository postRepository,
                      final Tag2PostRepository tag2PostRepository) {
        this.tagRepository = tagRepository;
        this.postRepository = postRepository;
        this.tag2PostRepository = tag2PostRepository;
    }

    public TagsResponse tagResponse(final String query) {

        List<TagResponseNative> tagResponseNativeList;

        if (query == null || query.equals("")) {
            tagResponseNativeList = tagRepository.getTagsWithWeights();
        } else {
            tagResponseNativeList = tagRepository.getQueryTagsWithWeights("%" + query.toLowerCase() + "%");
        }

        return new TagsResponse(tagRepository2tagRespDtoList(tagResponseNativeList));

    }

    private List<TagResponseDto> tagRepository2tagRespDtoList(final List<TagResponseNative> tagResponseNativeList) {
        List<TagResponseDto> tagResponseDtoList = new ArrayList<>();
        float maxNotNormedWeight = 0.00f;

        for (TagResponseNative tagResponse : tagResponseNativeList) {
            float notNormedWeight = tagResponse.getWeight();
            tagResponseDtoList.add(new TagResponseDto(tagResponse.getName(), notNormedWeight));
            maxNotNormedWeight = maxNotNormedWeight < notNormedWeight ? notNormedWeight : maxNotNormedWeight;
        }

        float k = 1.00f / maxNotNormedWeight;
        tagResponseDtoList.forEach(t -> {
            float normedWeight = t.getWeight() * k;
            t.setWeight(normedWeight < WEIGHT_THRESHOLD ? WEIGHT_THRESHOLD : normedWeight);
        });

        return tagResponseDtoList;
    }

}

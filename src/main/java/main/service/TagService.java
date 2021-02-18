package main.service;

import main.api.response.TagsResponse;
import main.dto.TagResponseDto;
import main.model.Tag;
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

    @Autowired
    public TagService(final TagRepository tagRepository,
                      final PostRepository postRepository,
                      final Tag2PostRepository tag2PostRepository) {
        this.tagRepository = tagRepository;
        this.postRepository = postRepository;
        this.tag2PostRepository = tag2PostRepository;
    }

    public TagsResponse tagResponse(final String query) {

        int amountOfPosts = postRepository.countAllPosts();
        List<TagResponseDto> tagResponseDtoList = tagRepository2tagRespDtoList(
                tag2PostRepository, tagRepository, amountOfPosts);

        if ((query != null) && (!query.equals(""))) {
            queryApply(tagResponseDtoList, query);
        }
        return new TagsResponse(tagResponseDtoList);


    }

    private List<TagResponseDto> tagRepository2tagRespDtoList(final Tag2PostRepository tag2PostRepository,
                                                              final TagRepository tagRepository,
                                                              final int amountOfPosts) {
        float maxdWeight = 0.00f;
        List<TagResponseDto> tagResponseDtoList = new ArrayList<>();

        Iterable<Tag> tags = tagRepository.findAll();
        for (Tag tag : tags) {
            int amountOfPostsForTag = tag2PostRepository.amountOfPostsForTag(tag.getId());

            float dWeight = (float) amountOfPostsForTag / (float) amountOfPosts;
            if (maxdWeight < dWeight) {
                maxdWeight = dWeight;
            }
            tagResponseDtoList.add(new TagResponseDto(tag.getName(), dWeight));
        }
        float k = 1.00f / maxdWeight;
        tagResponseDtoList.forEach(t -> {
            t.setWeight(t.getWeight() * k);
        });

        return tagResponseDtoList;
    }

    private void queryApply(final List<TagResponseDto> tagResponseDtoList, final String query) {
        String queryLow = query.toLowerCase();
        for (int i = 0; i < tagResponseDtoList.size(); i++) {
            System.out.println("query " + queryLow + " -> " + tagResponseDtoList.get(i).getName().toLowerCase() + " -> " + !tagResponseDtoList.get(i).getName().toLowerCase().contains(queryLow));
            if (!tagResponseDtoList.get(i).getName().toLowerCase().contains(queryLow)) {
                tagResponseDtoList.remove(i);
                i--;
            }
        }
    }

}

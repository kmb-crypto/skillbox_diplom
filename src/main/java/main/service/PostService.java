package main.service;

import main.api.response.PostsResponse;
import main.controller.PostModes;
import main.dto.PostsResponseDto;
import main.model.Post;
import main.repository.PostRepository;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PostService {

    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostsResponse getPosts(int offset, int limit, String mode) {
        int count = (int) postRepository.count();
        if (count == 0) {
            return new PostsResponse(0, new ArrayList<>());
        } else {
            if (limit == 0) {
                limit = 10;
            }
            Pageable pageable = PageRequest.of(offset, limit, createSort(mode));
            Collection<Post> postsCollection = postRepository.findAllPosts(pageable);

            List<PostsResponseDto> postsResponseDtoList = new ArrayList<>();

            postsCollection.forEach(p -> {
                System.out.println(p.getText());
                postEntityToResponse(p);
            });

//            System.out.println(postRepository.count());
//            Iterable<Post> postIterable = postRepository.findAll();
//            postRepository.findAll().forEach(s->{
//                System.out.println(s.getText());
//            });


            return new PostsResponse(count, new ArrayList<>());
        }
    }

    private Sort createSort(String mode) {
        if (mode.equals(PostModes.recent)) {
            return Sort.by(Sort.Direction.ASC, "time");
        } else if (mode.equals(PostModes.early)) {
            return Sort.by(Sort.Direction.DESC, "time");
        }
        return Sort.by(Sort.Direction.ASC, "time");
    }

    private PostsResponseDto postEntityToResponse(Post post) {
        PostsResponseDto postsResponseDto = new PostsResponseDto();
        postsResponseDto.setId(post.getId());
        postsResponseDto.setTime(post.getTime().toLocalDateTime());
        postsResponseDto.setTitle(post.getTitle());
        postsResponseDto.setAnnounce(createAnnounce(post.getText()));
        return postsResponseDto;
    }

    private String createAnnounce(String text) {
        if (text.length() > 150) {
            return Jsoup.parse(text).text().substring(0, 146) + "...";
        } else {
            return Jsoup.parse(text).text();
        }
    }

}


//        offset - сдвиг от 0 для постраничного вывода
//        limit - количество постов, которое надо вывести
//        status - статус модерации:
//        inactive - скрытые, ещё не опубликованы (is_active = 0)
//        pending - активные, ожидают утверждения модератором (is_active = 1,
//        moderation_status = NEW)
//        declined - отклонённые по итогам модерации (is_active = 1, moderation_status =
//        DECLINED)
//        published - опубликованные по итогам модерации (is_active = 1, moderation_status =
//        ACCEPTED)

package main.service;

import main.api.response.PostsResponse;
import main.dto.PostsResponseDto;
import main.dto.PostsResponseUserDto;
import main.model.Post;
import main.model.User;
import main.repository.PostRepository;
import main.repository.UserRepository;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class PostService {

    @Value("${mode.recent}")
    private String modeRecent;

    @Value("${mode.best}")
    private String modeBest;

    @Value("${mode.popular}")
    private String modePopular;

    @Value("${mode.early}")
    private String modeEarly;

    @Value("${blog.default.page.limit}")
    private int defaultPageLimit;

    @Value("${blog.announce.length}")
    private int announceLength;

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(final PostRepository postRepository, final UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostsResponse getPosts(int offset, int limit, final String mode) {

        if (postRepository.count() == 0) {
            return new PostsResponse(0, new ArrayList<>());
        } else {
            if (limit == 0) {
                limit = defaultPageLimit;
            }
            int count = postRepository.countAllPosts();
            List<PostsResponseDto> postsResponseDtoList = new ArrayList<>();

            if (mode.equals(modeBest)) {
                System.out.println("MODE BEST");
            } else if (mode.equals(modePopular)) {
                System.out.println("MODE POPULAR");
            } else {
                Collection<Post> postsCollection;
                if (mode.equals(modeRecent) || mode.equals("")) {
                    postsCollection = timeModePostCollection(postRepository,
                            PageRequest.of((offset / limit),
                                    limit,
                                    Sort.by(Sort.Direction.ASC, "time")));

                } else {
                    postsCollection = timeModePostCollection(postRepository,
                            PageRequest.of((offset / limit),
                                    limit,
                                    Sort.by(Sort.Direction.DESC, "time")));
                }

                postsCollection.forEach(p -> {
                    //System.out.println(p.getText());
                    postsResponseDtoList.add(postEntityToResponse(p, userRepository));
                });
            }
            return new PostsResponse(count, postsResponseDtoList);
        }
    }

    private Collection<Post> timeModePostCollection(PostRepository repository, Pageable pageable) {
        return repository.findAllPosts(pageable);
    }

    private PostsResponseDto postEntityToResponse(final Post post, final UserRepository userRepository) {
        PostsResponseDto postsResponseDto = new PostsResponseDto();
        postsResponseDto.setId(post.getId());
        postsResponseDto.setTime(post.getTime().toLocalDateTime());
        postsResponseDto.setTitle(post.getTitle());
        postsResponseDto.setAnnounce(createAnnounce(post.getText()));
        postsResponseDto.setLikeCount(0); //!!!!!!!!!!!!!!!!!!!!!!!
        postsResponseDto.setDislikeCount(0); //!!!!!!!!!!!!!!!!!!!!
        postsResponseDto.setCommentCount(0); //!!!!!!!!!!!!!!!!!!!!
        postsResponseDto.setViewCount(0); //!!!!!!!!!!!!!!!!!!!!!!!
        User user = post.getUser();
        postsResponseDto.setUserDto(new PostsResponseUserDto(user.getId(), user.getName()));

        return postsResponseDto;
    }

    private String createAnnounce(final String text) {
        if (text.length() > announceLength) {
            return Jsoup.parse(text).text().substring(0, announceLength - 4) + "...";
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

//        mode - режим вывода (сортировка):
//        recent - сортировать по дате публикации, выводить сначала новые (если mode не задан,
//        использовать это значение по умолчанию)
//        popular - сортировать по убыванию количества комментариев (посты без комментариев
//        выводить)
//        best - сортировать по убыванию количества лайков (посты без лайков и дизлайков
//        выводить)
//        early - сортировать по дате публикации, выводить сначала старые

//{
//        "count": 3,
//        "posts": [
//        {
//              "id": 31,
//              "timestamp": 1592338706,
//              "title": "Заголовок поста",
//              "announce": "Текст анонса (часть основного текста) поста без HTML-тэгов (HTML
//              тэги необходимо удалить из текста анонса)",
//              "likeCount": 36,
//              "dislikeCount": 3,
//              "commentCount": 15,
//              "viewCount": 55,
//              "user":
//              {
//                  "id": 88,
//                  "name": "Дмитрий Петров"
//              }
//        },
//        {...}
//        ]
//}
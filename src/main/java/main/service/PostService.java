package main.service;

import main.api.response.PostsResponse;
import main.dto.PostsResponseDto;
import main.dto.PostsResponseUserDto;
import main.model.Post;
import main.model.User;
import main.repository.PostCommentRepository;
import main.repository.PostRepository;
import main.repository.PostVotesRepository;
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

    @Value("${blog.default.page.limit}")
    private int defaultPageLimit;

    @Value("${blog.announce.length}")
    private int announceLength;

    private final PostRepository postRepository;
    private final PostVotesRepository postVotesRepository;
    private final PostCommentRepository postCommentRepository;


    @Autowired
    public PostService(final PostRepository postRepository,
                       final PostVotesRepository postVotesRepository,
                       final PostCommentRepository postCommentRepository) {
        this.postRepository = postRepository;
        this.postVotesRepository = postVotesRepository;
        this.postCommentRepository = postCommentRepository;
    }

    public PostsResponse getPostsResponse(final int offset, int limit, final String mode) {
        int count = postRepository.countAllAvailablePosts();
        if (count == 0) {
            return new PostsResponse(count, new ArrayList<>());
        } else {

            List<PostsResponseDto> postsResponseDtoList = new ArrayList<>();
            Collection<Post> postsCollection = new ArrayList<>();

            limit = limitCheck(limit);

            switch (mode) {
                case "best":
                    postsCollection = postRepository.findBestPosts(PageRequest.of((offset / limit), limit));
                    break;
                case "popular":
                    postsCollection = postRepository.findPopularPosts(PageRequest.of((offset / limit), limit));
                    break;
                case "recent":
                    postsCollection = timeModePostCollection(postRepository,
                            PageRequest.of((offset / limit),
                                    limit,
                                    Sort.by(Sort.Direction.DESC, "time")));
                    break;
                case "early":
                    postsCollection = timeModePostCollection(postRepository,
                            PageRequest.of((offset / limit),
                                    limit,
                                    Sort.by(Sort.Direction.ASC, "time")));
            }
            postsCollection.forEach(p -> {
                postsResponseDtoList.add(postEntityToResponse(p, postVotesRepository, postCommentRepository));
            });
            return new PostsResponse(count, postsResponseDtoList);
        }

    }

    public PostsResponse getPostsByDateResponse(final int offset, int limit, final String date) {
        int count = postRepository.countAllAvailablePostsByDate(date);
        if (count == 0) {
            return new PostsResponse(count, new ArrayList<>());
        } else {
            List<PostsResponseDto> postsResponseDtoList = new ArrayList<>();
            Collection<Post> postsCollection;

            limit = limitCheck(limit);

            postsCollection = postRepository.findAllPostsByDate(date, PageRequest.of((offset / limit), limit));

            postsCollection.forEach(p -> {
                postsResponseDtoList.add(postEntityToResponse(p, postVotesRepository, postCommentRepository));
            });
            return new PostsResponse(count, postsResponseDtoList);
        }
    }

    public PostsResponse getPostsByQueryResponse(final int offset, int limit, String query) {
        query = query.trim().replaceAll("\\s+", " ");
        if (query.equals("")) {
            return getPostsResponse(offset, limit, "recent");
        } else {
            int count = postRepository.countAllAvailablePostsByQuery(query);
            if (count == 0) {
                return new PostsResponse(count, new ArrayList<>());
            } else {
                List<PostsResponseDto> postsResponseDtoList = new ArrayList<>();
                Collection<Post> postsCollection;

                limit = limitCheck(limit);

                postsCollection = postRepository.findAllPostsByQuery(query, PageRequest.of((offset / limit), limit));
                postsCollection.forEach(p -> {
                    postsResponseDtoList.add(postEntityToResponse(p, postVotesRepository, postCommentRepository));
                });

                return new PostsResponse(count, postsResponseDtoList);
            }
        }
    }

    public PostsResponse getPostsByTagResponse(final int offset, int limit, final String tag) {
        List<PostsResponseDto> postsResponseDtoList = new ArrayList<>();
        Collection<Post> postsCollection;

        limit = limitCheck(limit);

        postsCollection = postRepository.findAllPostsByTag(tag, PageRequest.of((offset / limit), limit));
        postsCollection.forEach(p -> {
            postsResponseDtoList.add(postEntityToResponse(p, postVotesRepository, postCommentRepository));
        });
        return new PostsResponse(postRepository.countAllAvailablePostsByTag(tag), postsResponseDtoList);

    }

    private Collection<Post> timeModePostCollection(final PostRepository repository, final Pageable pageable) {
        return repository.findAllPosts(pageable);
    }

    private PostsResponseDto postEntityToResponse(final Post post,
                                                  final PostVotesRepository postVotesRepository,
                                                  final PostCommentRepository postCommentRepository) {
        int postId = post.getId();
        PostsResponseDto postsResponseDto = new PostsResponseDto();
        postsResponseDto.setId(postId);
        postsResponseDto.setTime(post.getTime().toLocalDateTime());
        postsResponseDto.setTitle(post.getTitle());
        postsResponseDto.setAnnounce(createAnnounce(post.getText()));
        postsResponseDto.setLikeCount(postVotesRepository.countLikes(postId));
        postsResponseDto.setDislikeCount(postVotesRepository.countDislikes(postId));
        postsResponseDto.setCommentCount(postCommentRepository.countComments(postId));
        postsResponseDto.setViewCount(post.getViewCount());
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

    private int limitCheck(int limit) {
        return limit == 0 ? defaultPageLimit : limit;
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
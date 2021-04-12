package main.service;

import main.InitSettings;
import main.api.request.ModerationRequest;
import main.api.request.PostRequest;
import main.api.response.*;
import main.dto.CommentsResponseDto;
import main.dto.CommentsResponseUserDto;
import main.dto.PostsResponseDto;
import main.dto.PostsResponseUserDto;
import main.model.*;
import main.repository.*;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public class PostService {

    @Value("${blog.default.page.limit}")
    private int defaultPageLimit;

    @Value("${blog.announce.length}")
    private int announceLength;

    private static final String DEFAULT_MODE = "recent";
    private static final int MIN_TITLE_LENGHT = 3;
    private static final int MIN_TEXT_LENGHT = 50;

    private final PostRepository postRepository;
    private final PostVotesRepository postVotesRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final GlobalSettingsRepository globalSettingsRepository;

    private final TagService tagService;


    @Autowired
    public PostService(final PostRepository postRepository,
                       final PostVotesRepository postVotesRepository,
                       final CommentRepository commentRepository,
                       final UserRepository userRepository,
                       final TagRepository tagRepository,
                       final GlobalSettingsRepository globalSettingsRepository,
                       final TagService tagService) {
        this.postRepository = postRepository;
        this.postVotesRepository = postVotesRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.globalSettingsRepository = globalSettingsRepository;
        this.tagService = tagService;
    }

    public PostsResponse getPostsResponse(final Integer offset, final Integer limit, final String mode) {
        int count = postRepository.countAllAvailablePosts();
        if (count == 0) {
            return new PostsResponse(count, new ArrayList<>());
        } else {
            List<PostsResponseDto> postsResponseDtoList = new ArrayList<>();
            Collection<Post> postsCollection = new ArrayList<>();

            switch (mode) {
                case "best":
                    postsCollection = postRepository.findBestPosts(PageRequest.of((offset / limit), limit));
                    break;
                case "popular":
                    postsCollection = postRepository.findPopularPosts(PageRequest.of((offset / limit), limit));
                    break;
                case "recent":
                    postsCollection = getTimeModePostCollection(PageRequest.of((offset / limit),
                            limit, Sort.by(Sort.Direction.DESC, "time")));
                    break;
                case "early":
                    postsCollection = getTimeModePostCollection(PageRequest.of((offset / limit),
                            limit, Sort.by(Sort.Direction.ASC, "time")));
            }
            postsCollection.forEach(p -> postsResponseDtoList.add(postEntityToResponse(p)));
            return new PostsResponse(count, postsResponseDtoList);
        }
    }

    public PostsResponse getPostsByDateResponse(final Integer offset, final Integer limit, final String date) {
        int count = postRepository.countAllAvailablePostsByDate(date);
        if (count == 0) {
            return new PostsResponse(count, new ArrayList<>());
        } else {
            List<PostsResponseDto> postsResponseDtoList = new ArrayList<>();
            Collection<Post> postsCollection;

            postsCollection = postRepository.findAllPostsByDate(date, PageRequest.of((offset / limit), limit));

            postsCollection.forEach(p -> postsResponseDtoList.add(postEntityToResponse(p)));
            return new PostsResponse(count, postsResponseDtoList);
        }
    }

    public PostsResponse getPostsByQueryResponse(final Integer offset, final Integer limit, String query) {
        query = query.trim().replaceAll("\\s+", " ");
        if (query.equals("")) {
            return getPostsResponse(offset, limit, DEFAULT_MODE);
        } else {
            int count = postRepository.countAllAvailablePostsByQuery(query);
            if (count == 0) {
                return new PostsResponse(count, new ArrayList<>());
            } else {
                List<PostsResponseDto> postsResponseDtoList = new ArrayList<>();
                Collection<Post> postsCollection;

                postsCollection = postRepository.findAllPostsByQuery(query, PageRequest.of((offset / limit), limit));
                postsCollection.forEach(p -> postsResponseDtoList.add(postEntityToResponse(p)));

                return new PostsResponse(count, postsResponseDtoList);
            }
        }
    }

    public PostsResponse getPostsByTagResponse(final Integer offset, final Integer limit, final String tag) {
        List<PostsResponseDto> postsResponseDtoList = new ArrayList<>();
        Collection<Post> postsCollection;

        postsCollection = postRepository.findAllPostsByTag(tag, PageRequest.of((offset / limit), limit));
        postsCollection.forEach(p -> postsResponseDtoList.add(postEntityToResponse(p)));
        return new PostsResponse(postRepository.countAllAvailablePostsByTag(tag), postsResponseDtoList);

    }

    public PostByIdResponse getPostById(final int id, Principal principal) {
        Post post = postRepository.findPostById(id);

        if (post == null) {
            return null;
        }

        if (principal != null) {
            User currentUser = userRepository.findByEmail(principal.getName()).get();
            if (currentUser.getIsModerator() == 1 || currentUser.getId() == post.getUser().getId()) {
                return postEntityToResponseById(post, id);
            }
        }

        postRepository.updateViewCount(id);
        return postEntityToResponseById(post, id);
    }


    public PostsResponse getMyPosts(
            final Integer offset, final Integer limit, final String status, final Principal principal) {
        String email = principal.getName();
        int count = 0;

        List<Post> postsCollection;
        switch (status) {
            case "inactive":
                count = postRepository.countMyInactivePosts(email);
                if (count == 0) {
                    return new PostsResponse(0, new ArrayList<>());
                } else {
                    postsCollection = postRepository.findMyInactivePosts(email,
                            PageRequest.of((offset / limit), limit));
                    break;
                }
            case "pending":
                count = postRepository.countMyPendingPosts(email);
                if (count == 0) {
                    return new PostsResponse(0, new ArrayList<>());
                } else {
                    postsCollection = postRepository.findMyPendingPosts(email,
                            PageRequest.of((offset / limit), limit));
                    break;
                }
            case "declined":
                count = postRepository.countMyDeclinedPosts(email);
                if (count == 0) {
                    return new PostsResponse(0, new ArrayList<>());
                } else {
                    postsCollection = postRepository.findMyDeclinedPosts(email,
                            PageRequest.of((offset / limit), limit));
                    break;
                }
            case "published":
                count = postRepository.countMyPublishedPosts(email);
                if (count == 0) {
                    return new PostsResponse(0, new ArrayList<>());
                } else {
                    postsCollection = postRepository.findMyPublishedPosts(email,
                            PageRequest.of((offset / limit), limit));
                    break;
                }
            default:
                postsCollection = new ArrayList<>();
        }

        List<PostsResponseDto> postsResponseDtoList = new ArrayList<>();
        postsCollection.forEach(p -> postsResponseDtoList.add(postEntityToResponse(p)));
        return new PostsResponse(count, postsResponseDtoList);

    }

    public PostsResponse getModerationPosts(
            final Integer offset, final Integer limit, final String status, final Principal principal) {
        String email = principal.getName();
        int count = 0;

        List<Post> postsCollection;
        switch (status) {
            case ("new"):
                count = postRepository.countAllNewPosts();
                if (count == 0) {
                    return new PostsResponse(0, new ArrayList<>());
                } else {
                    postsCollection = postRepository.findAllNewPosts(PageRequest.of((offset / limit), limit));
                    break;
                }
            case ("declined"):
                count = postRepository.countDeclinedPostsByMe(email);
                if (count == 0) {
                    return new PostsResponse(0, new ArrayList<>());
                } else {
                    postsCollection = postRepository
                            .findDeclinedPostsByMe(email, PageRequest.of((offset / limit), limit));
                    break;
                }
            case ("accepted"):
                count = postRepository.countAcceptedPostsByMe(email);
                if (count == 0) {
                    return new PostsResponse(0, new ArrayList<>());
                } else {
                    postsCollection = postRepository
                            .findAcceptedPostsByMe(email, PageRequest.of((offset / limit), limit));
                    break;
                }
            default:
                postsCollection = new ArrayList<>();
        }
        List<PostsResponseDto> postsResponseDtoList = new ArrayList<>();
        postsCollection.forEach(p -> postsResponseDtoList.add(postEntityToResponse(p)));
        return new PostsResponse(count, postsResponseDtoList);
    }

    public PostProcessingResponse createPost(final PostRequest postRequest, final Principal principal) {
        HashMap<String, String> errors = checkPostRequest(postRequest);
        if (errors != null) {
            return new PostProcessingResponse(false, errors);
        } else {
            addNewPost(postRequest, principal);
            return new PostProcessingResponse(true);
        }
    }

    public PostProcessingResponse editPost(final int id, final PostRequest postRequest, final Principal principal) {
        HashMap<String, String> errors = checkPostRequest(postRequest);
        if (errors != null) {
            return new PostProcessingResponse(false, errors);
        } else {
            Post editablePost = postRepository.findPostById(id);
            setEditablePost(editablePost, postRequest, principal);
            tagService.removeOrphanTags();
            return new PostProcessingResponse(true);
        }

    }

    public ModerationResponse setModerationStatus(final ModerationRequest moderationRequest, final Principal principal) {
        Post post = postRepository.findPostById(moderationRequest.getPostId());
        String decision = moderationRequest.getDecision();

        if (post == null) {
            return new ModerationResponse(false);
        }

        return saveModerationStatus(moderationRequest, post, principal);
    }

    // PRIVATE PART ---------------------------------------------------------------------------------------------------

    private Collection<Post> getTimeModePostCollection(final Pageable pageable) {
        return postRepository.findAllPosts(pageable);
    }

    private PostsResponseDto postEntityToResponse(final Post post) {
        int postId = post.getId();
        PostsResponseDto postsResponseDto = new PostsResponseDto();
        postsResponseDto.setId(postId);
        postsResponseDto.setTimestamp(post.getTime().toLocalDateTime().toEpochSecond(ZoneOffset.of("+03:00")));
        postsResponseDto.setTitle(post.getTitle());
        postsResponseDto.setAnnounce(createAnnounce(post.getText()));
        postsResponseDto.setLikeCount(postVotesRepository.countLikes(postId));
        postsResponseDto.setDislikeCount(postVotesRepository.countDislikes(postId));
        postsResponseDto.setCommentCount(commentRepository.countComments(postId));
        postsResponseDto.setViewCount(post.getViewCount());
        User user = post.getUser();
        postsResponseDto.setUserDto(new PostsResponseUserDto(user.getId(), user.getName()));

        return postsResponseDto;
    }

    private PostByIdResponse postEntityToResponseById(final Post post, final int id) {
        PostByIdResponse postByIdResponse = new PostByIdResponse();

        postByIdResponse.setId(id);
        postByIdResponse.setTimestamp(post.getTime().toLocalDateTime().toEpochSecond(ZoneOffset.of("+03:00")));
        postByIdResponse.setActive(post.getIsActive() != 0);

        User user = post.getUser();

        postByIdResponse.setUserDto(new PostsResponseUserDto(user.getId(), user.getName()));
        postByIdResponse.setTitle(post.getTitle());
        postByIdResponse.setText(post.getText());

        int likes = 0;
        int dislikes = 0;
        for (PostVote vote : post.getPostVotes()) {
            if ((vote.getValue() == 1)) {
                likes++;
            } else {
                dislikes++;
            }
        }

        postByIdResponse.setLikeCount(likes);
        postByIdResponse.setDislikeCount(dislikes);

        postByIdResponse.setViewCount(post.getViewCount());

        List<CommentsResponseDto> commentsResponseDtoList = new ArrayList<>();

        for (PostComment comment : post.getPostComments()) {
            User commentUser = comment.getUser();
            commentsResponseDtoList.add(new CommentsResponseDto(comment.getId(),
                    comment.getTime().toLocalDateTime().toEpochSecond(ZoneOffset.of("+03:00")),
                    comment.getText(),
                    new CommentsResponseUserDto(commentUser.getId(), commentUser.getName(), commentUser.getPhoto())));
        }

        postByIdResponse.setComments(commentsResponseDtoList);

        List<String> tagResponse = new ArrayList<>();

        for (Tag tag : post.getTags()) {
            tagResponse.add(tag.getName());
        }

        postByIdResponse.setTags(tagResponse);

        return postByIdResponse;
    }

    private String createAnnounce(final String text) {
        String textWithoutHtml = Jsoup.parse(text).text();

        return textWithoutHtml.length() > announceLength ?
                textWithoutHtml.substring(0, announceLength - 4) + "..." : textWithoutHtml;
    }

    private HashMap<String, String> checkPostRequest(final PostRequest postRequest) {
        HashMap<String, String> errors = new HashMap<>();
        if (postRequest.getTitle().length() < MIN_TITLE_LENGHT) {

            errors.put("title", "Заголовок не установлен");
        }
        if (postRequest.getText().length() < MIN_TEXT_LENGHT) {

            errors.put("text", "Текст публикации слишком короткий");
        }

        return errors.size() > 0 ? errors : null;
    }

    private void addNewPost(final PostRequest postRequest, final Principal principal) {
        Post post = new Post();
        post.setIsActive(postRequest.getActive());
        post.setModerationStatus(globalSettingsRepository.findByCode(InitSettings.POST_PREMODERATION_CODE)
                .getValue().equals(InitSettings.YES) ? ModerationStatus.NEW : ModerationStatus.ACCEPTED);
        post.setUser(userRepository.findByEmail(principal.getName()).get());
        post.setTime(checkTimestamp(postRequest.getTimestamp()));
        post.setTitle(postRequest.getTitle());
        post.setText(postRequest.getText());

        List<String> tags = postRequest.getTags();

        if (tags.size() > 0) {

            tags.forEach(t -> tagRepository.saveIgnoreDuplicateKey(t.toLowerCase()));
            post.setTags(tagRepository.findTagsIdByNameIn(tags));
        }

        postRepository.save(post);
    }

    private void setEditablePost(final Post post, final PostRequest postRequest, final Principal principal) {
        post.setIsActive(postRequest.getActive());
        User currentUser = userRepository.findByEmail(principal.getName()).get();
        if (currentUser.getIsModerator() == 0 &&
                globalSettingsRepository.findByCode(InitSettings.POST_PREMODERATION_CODE).getValue()
                        .equals(InitSettings.YES)) {
            post.setModerationStatus(ModerationStatus.NEW);
            post.setModeratorId(null);
        } else {
            post.setModeratorId(post.getModeratorId() == null ? null : currentUser.getId());
        }
        post.setTime(checkTimestamp(postRequest.getTimestamp()));
        post.setTitle(postRequest.getTitle());
        post.setText(postRequest.getText());
        List<String> tags = postRequest.getTags();

        if (tags.size() > 0) {

            tags.forEach(t -> tagRepository.saveIgnoreDuplicateKey(t.toLowerCase()));
            post.setTags(tagRepository.findTagsIdByNameIn(tags));
        } else {
            post.setTags(new ArrayList<>());
        }
        postRepository.save(post);
    }

    private Timestamp checkTimestamp(final long time) {
        Instant instant = Instant.ofEpochSecond(time);
        Instant now = Instant.now();

        return instant.isBefore(now) ? Timestamp.from(now) : Timestamp.from(instant);
    }

    private List<Tag> getTags(final List<String> tags) {
        List<Tag> postTags = new ArrayList<>();
        tags.forEach(t -> postTags.add(new Tag(t.toLowerCase())));
        return postTags;
    }

    private ModerationResponse saveModerationStatus(final ModerationRequest moderationRequest,
                                                    final Post post,
                                                    final Principal principal) {
        post.setModeratorId(userRepository.findByEmail(principal.getName()).get().getId());
        post.setModerationStatus(moderationRequest
                .getDecision().equals("accept") ? ModerationStatus.ACCEPTED : ModerationStatus.DECLINED);
        postRepository.save(post);
        return new ModerationResponse(true);
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

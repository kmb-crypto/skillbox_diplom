package main.service;

import main.InitSettings;
import main.api.request.ModerationRequest;
import main.api.request.PostRequest;
import main.api.response.ModerationResponse;
import main.api.response.PostByIdResponse;
import main.api.response.PostProcessingResponse;
import main.api.response.PostsResponse;
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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Value("${blog.default.page.limit:10}")
    private int defaultPageLimit;

    @Value("${blog.announce.length:150}")
    private int announceLength;

    @Value("${blog.min.title.length:3}")
    private int minTitleLength;

    @Value("${blog.min.text.length:50}")
    private int minTextLength;


    private static final String DEFAULT_MODE = "recent";

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
            return getEmptyPostResponse();
        }
        switch (mode) {
            case "best":
                return getPostsPage(count, postRepository.findBestPosts(PageRequest.of((offset / limit), limit)));
            case "popular":
                return getPostsPage(count, postRepository.findPopularPosts(PageRequest.of((offset / limit), limit)));
            case "recent":
                return getPostsPage(count, getTimeModePostCollection(PageRequest.of((offset / limit),
                        limit, Sort.by(Sort.Direction.DESC, "time"))));
            case "early":
                return getPostsPage(count, getTimeModePostCollection(PageRequest.of((offset / limit),
                        limit, Sort.by(Sort.Direction.ASC, "time"))));
        }
        return getEmptyPostResponse();
    }

    public PostsResponse getPostsByDateResponse(final Integer offset, final Integer limit, final String date) {
        int count = postRepository.countAllAvailablePostsByDate(date);
        if (count == 0) {
            return getEmptyPostResponse();
        }
        return getPostsPage(count, postRepository.findAllPostsByDate(date, PageRequest.of((offset / limit), limit)));
    }

    public PostsResponse getPostsByQueryResponse(final Integer offset, final Integer limit, String query) {
        query = query.trim().replaceAll("\\s+", " ");
        if (query.equals("")) {
            return getPostsResponse(offset, limit, DEFAULT_MODE);
        } else {
            int count = postRepository.countAllAvailablePostsByQuery(query);
            if (count == 0) {
                return getEmptyPostResponse();
            }
            return getPostsPage(count,
                    postRepository.findAllPostsByQuery(query, PageRequest.of((offset / limit), limit)));
        }
    }

    public PostsResponse getPostsByTagResponse(final Integer offset, final Integer limit, final String tag) {
        return getPostsPage(postRepository.countAllAvailablePostsByTag(tag),
                postRepository.findAllPostsByTag(tag, PageRequest.of((offset / limit), limit)));
    }

    public Optional<PostByIdResponse> getPostById(final int id, Principal principal) {
        Post post = postRepository.findPostById(id);

        if (post == null) {
            return Optional.empty();
        }

        if (principal != null) {
            User currentUser = userRepository.findByEmail(principal.getName()).get();
            if (currentUser.getIsModerator() == 1 || currentUser.getId() == post.getUser().getId()) {
                return Optional.of(postEntityToResponseById(post, id));
            }
        }

        postRepository.updateViewCount(id);
        return Optional.of(postEntityToResponseById(post, id));
    }


    public PostsResponse getMyPosts(
            final Integer offset, final Integer limit, final String status, final Principal principal) {
        String email = principal.getName();
        int count = 0;

        switch (status) {
            case "inactive":
                count = postRepository.countMyInactivePosts(email);
                return count == 0 ? getEmptyPostResponse()
                        : getPostsPage(count, postRepository.findMyInactivePosts(email,
                        PageRequest.of((offset / limit), limit)));
            case "pending":
                count = postRepository.countMyPendingPosts(email);
                return count == 0 ? getEmptyPostResponse()
                        : getPostsPage(count, postRepository.findMyPendingPosts(email,
                        PageRequest.of((offset / limit), limit)));
            case "declined":
                count = postRepository.countMyDeclinedPosts(email);
                return count == 0 ? getEmptyPostResponse()
                        : getPostsPage(count, postRepository.findMyDeclinedPosts(email,
                        PageRequest.of((offset / limit), limit)));
            case "published":
                count = postRepository.countMyPublishedPosts(email);
                return count == 0 ? getEmptyPostResponse()
                        : getPostsPage(count, postRepository.findMyPublishedPosts(email,
                        PageRequest.of((offset / limit), limit)));
        }
        return getEmptyPostResponse();
    }

    public PostsResponse getModerationPosts(
            final Integer offset, final Integer limit, final String status, final Principal principal) {
        String email = principal.getName();
        int count = 0;

        switch (status) {
            case ("new"):
                count = postRepository.countAllNewPosts();
                return count == 0 ? getEmptyPostResponse()
                        : getPostsPage(count, postRepository.findAllNewPosts(PageRequest.of((offset / limit), limit)));
            case ("declined"):
                count = postRepository.countDeclinedPostsByMe(email);
                return count == 0 ? getEmptyPostResponse()
                        : getPostsPage(count, postRepository
                        .findDeclinedPostsByMe(email, PageRequest.of((offset / limit), limit)));
            case ("accepted"):
                count = postRepository.countAcceptedPostsByMe(email);
                return count == 0 ? getEmptyPostResponse()
                        : getPostsPage(count, postRepository
                        .findAcceptedPostsByMe(email, PageRequest.of((offset / limit), limit)));
        }
        return getEmptyPostResponse();
    }

    public PostProcessingResponse createPost(final PostRequest postRequest, final Principal principal) {
        Optional<HashMap<String, String>> errors = checkPostRequest(postRequest);
        if (errors.isPresent()) {
            return new PostProcessingResponse(false, errors.get());
        } else {
            addNewPost(postRequest, principal);
            return new PostProcessingResponse(true);
        }
    }

    public PostProcessingResponse editPost(final int id, final PostRequest postRequest, final Principal principal) {
        Optional<HashMap<String, String>> errors = checkPostRequest(postRequest);
        if (errors.isPresent()) {
            return new PostProcessingResponse(false, errors.get());
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

    private PostsResponse getEmptyPostResponse() {
        return new PostsResponse(0, new ArrayList<>());
    }

    private List<Post> getTimeModePostCollection(final Pageable pageable) {
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

    private PostsResponse getPostsPage(final int count, List<Post> postsCollection) {
        List<PostsResponseDto> postsResponseDtoList = new ArrayList<>();
        postsCollection.forEach(p -> postsResponseDtoList.add(postEntityToResponse(p)));
        return new PostsResponse(count, postsResponseDtoList);
    }

    private String createAnnounce(final String text) {
        String textWithoutHtml = Jsoup.parse(text).text();

        return textWithoutHtml.length() > announceLength ?
                textWithoutHtml.substring(0, announceLength - 4) + "..." : textWithoutHtml;
    }

    private Optional<HashMap<String, String>> checkPostRequest(final PostRequest postRequest) {
        HashMap<String, String> errors = new HashMap<>();
        if (postRequest.getTitle().length() < minTitleLength) {

            errors.put("title", "Заголовок не установлен");
        }
        if (postRequest.getText().length() < minTextLength) {

            errors.put("text", "Текст публикации слишком короткий");
        }

        return errors.size() > 0 ? Optional.of(errors) : Optional.empty();
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

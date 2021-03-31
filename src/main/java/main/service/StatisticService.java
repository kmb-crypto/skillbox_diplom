package main.service;

import main.api.response.StatisticResponse;
import main.dto.StatisticNative;
import main.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.ZoneOffset;

@Service
public class StatisticService {

    private final UserRepository userRepository;

    public StatisticService(final UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public StatisticResponse getStatisticResponse(final Principal principal) {
        StatisticNative statisticNative =
                userRepository.getMyStatistic(userRepository.findByEmail(principal.getName()).get().getId());
        return statisticNative2Response(statisticNative);
    }

    private StatisticResponse statisticNative2Response(final StatisticNative statisticNative) {
        StatisticResponse statisticResponse = new StatisticResponse();

        Integer postsCount = statisticNative.getPostsCount();
        Integer likesCount = statisticNative.getLikesCount();
        Integer dislikesCount = statisticNative.getDislikesCount();
        Integer viewsCount = statisticNative.getViewsCount();
        Timestamp firstPublicationTime = statisticNative.getFirstPublicationTime();

        statisticResponse.setPostsCount(postsCount == null ? 0 : postsCount);
        statisticResponse.setLikesCount(likesCount == null ? 0 : likesCount);
        statisticResponse.setDislikesCount(dislikesCount == null ? 0 : dislikesCount);
        statisticResponse.setViewsCount(viewsCount == null ? 0 : viewsCount);
        statisticResponse.setFirstPublication(firstPublicationTime == null ? 0 : firstPublicationTime
                .toLocalDateTime().toEpochSecond(ZoneOffset.of("+03:00")));
        return statisticResponse;
    }
}

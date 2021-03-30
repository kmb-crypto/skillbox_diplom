package main.service;

import main.api.response.StatisticResponse;
import main.dto.StatisticNative;
import main.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.ZoneOffset;

@Service
public class StatisticService {

    private final UserRepository userRepository;

    public StatisticService(final UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public StatisticResponse getStatisticResponse(final Principal principal) {
        StatisticResponse statisticResponse = new StatisticResponse();
        StatisticNative statisticNative =
                userRepository.getMyStatistic(userRepository.findByEmail(principal.getName()).get().getId());

        statisticResponse.setPostsCount(statisticNative.getPostsCount());
        statisticResponse.setLikesCount(statisticNative.getLikesCount());
        statisticResponse.setDislikesCount(statisticNative.getDislikesCount());
        statisticResponse.setViewsCount(statisticNative.getViewsCount());
        statisticResponse.setFirstPublication(statisticNative.getFirstPublication()
                .toLocalDateTime().toEpochSecond(ZoneOffset.of("+03:00")));

        return statisticResponse;
    }
}

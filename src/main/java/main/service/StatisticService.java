package main.service;

import com.fasterxml.jackson.annotation.OptBoolean;
import main.api.response.StatisticResponse;
import main.dto.StatisticNative;
import main.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.Optional;

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

        Optional<Timestamp> optionalTimestamp = statisticNative.getFirstPublication();
        Timestamp firstPublication = optionalTimestamp.isPresent() ? optionalTimestamp.get() : null;

        System.out.println(statisticNative.getPostsCount().get().getClass());

        statisticResponse.setPostsCount(statisticNative.getPostsCount().orElse(0));
        statisticResponse.setLikesCount(statisticNative.getLikesCount().orElse(0));
        statisticResponse.setDislikesCount(statisticNative.getDislikesCount().orElse(0));
        statisticResponse.setViewsCount(statisticNative.getViewsCount().orElse(0));
        statisticResponse.setFirstPublication(firstPublication == null ? 0 : firstPublication
                .toLocalDateTime().toEpochSecond(ZoneOffset.of("+03:00")));
        return statisticResponse;
    }
}

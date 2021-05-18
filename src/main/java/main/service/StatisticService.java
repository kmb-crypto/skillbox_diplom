package main.service;

import main.InitSettings;
import main.api.response.StatisticResponse;
import main.dto.StatisticNative;
import main.repository.GlobalSettingsRepository;
import main.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class StatisticService {

    private final UserRepository userRepository;
    private final GlobalSettingsRepository globalSettingsRepository;

    public StatisticService(final UserRepository userRepository, final GlobalSettingsRepository globalSettingsRepository) {
        this.userRepository = userRepository;
        this.globalSettingsRepository = globalSettingsRepository;
    }

    public StatisticResponse getMyStatisticResponse(final Principal principal) {
        StatisticNative statisticNative =
                userRepository.getMyStatistic(userRepository.findByEmail(principal.getName()).get().getId());
        return statisticNative2Response(statisticNative);
    }

    public Optional<StatisticResponse> getAllStatisticResponse(final Principal principal) {

        if (globalSettingsRepository.findByCode(InitSettings.STATISTIC_IS_PUBLIC_CODE).getValue().equals(InitSettings.NO)) {
            if (principal == null || userRepository.findByEmail(principal.getName()).get().getIsModerator() == 0) {
                return Optional.empty();
            }
        }
        return Optional.of(statisticNative2Response(userRepository.getAllStatistic()));
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

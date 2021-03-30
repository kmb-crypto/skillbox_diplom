package main.dto;

import java.sql.Timestamp;
import java.util.Optional;

public interface StatisticNative {
    Optional<Integer> getPostsCount();
    Optional<Integer> getLikesCount();
    Optional<Integer> getDislikesCount();
    Optional<Integer> getViewsCount();
    Optional<Timestamp> getFirstPublication();
}

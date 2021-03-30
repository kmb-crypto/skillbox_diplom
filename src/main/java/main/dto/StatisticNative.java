package main.dto;

import java.sql.Timestamp;

public interface StatisticNative {
    Integer getPostsCount();
    Integer getLikesCount();
    Integer getDislikesCount();
    Integer getViewsCount();
    Timestamp getFirstPublication();
}

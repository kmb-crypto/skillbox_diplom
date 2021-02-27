package main.service;

import main.api.response.CalendarResponse;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.TreeSet;

@Service
public class CalendarService {
    public CalendarResponse getCalendarResponse(Integer year) {
        CalendarResponse calendarResponse = new CalendarResponse();
        TreeSet<Integer> years = new TreeSet<>();

        HashMap<String, Integer> posts = new HashMap<>();

        calendarResponse.setYears(years);
        calendarResponse.setPosts(posts);
        return calendarResponse;

    }

}

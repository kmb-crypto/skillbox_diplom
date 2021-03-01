package main.service;

import main.api.response.CalendarResponse;
import main.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class CalendarService {

    private final PostRepository postRepository;

    @Autowired
    public CalendarService(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    public CalendarResponse getCalendarResponse(Integer year) {
        CalendarResponse calendarResponse = new CalendarResponse();
        TreeSet<Integer> years = new TreeSet<>();
        HashMap<String, Integer> posts = new HashMap<>();

        if (year == null || year.equals("")) {
            year = getCurrentYear();
        }

        postRepository.getCalendarYears().forEach(y -> {
            years.add(y.getYear());
        });

        postRepository.getAmountOfPostsByDay(year).forEach(a -> {
            posts.put(a.getDate(), a.getAmount());
        });

        calendarResponse.setYears(years);
        calendarResponse.setPosts(posts);
        return calendarResponse;

    }

    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTime(new Date());
        return calendar.get(Calendar.YEAR);
    }

}

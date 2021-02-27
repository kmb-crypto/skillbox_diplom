package main.controller;

import main.api.response.CalendarResponse;
import main.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiCalendarController {

    private final CalendarService calendarService;

    @Autowired
    public ApiCalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping(value = "/calendar")
    private CalendarResponse getCalendarNumberOfPosts(
            @RequestParam(value = "year", required = false) final Integer year) {
        return calendarService.getCalendarResponse(year);
    }
}

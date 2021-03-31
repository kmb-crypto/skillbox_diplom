package main.controller;

import main.api.response.StatisticResponse;
import main.service.StatisticService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class StatisticController {

    private final StatisticService statisticService;

    public StatisticController(final StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping(value = "/statistics/my")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity getMyStatistic(final Principal principal) {
        return new ResponseEntity(statisticService.getMyStatisticResponse(principal), HttpStatus.OK);
    }

    @GetMapping(value = "/statistics/all")
    public ResponseEntity getAllStatistic(final Principal principal) {
        StatisticResponse statisticResponse = statisticService.getAllStatisticResponse(principal);
        return statisticResponse == null ? ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null) :
                new ResponseEntity(statisticResponse, HttpStatus.OK);
    }
}

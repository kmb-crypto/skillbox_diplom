package main.controller;

import main.api.response.StatisticResponse;
import main.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StatisticController {

    private final StatisticService statisticService;

    @Autowired
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
        Optional<StatisticResponse> statisticResponse = statisticService.getAllStatisticResponse(principal);
        return statisticResponse.isEmpty() ? ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null) :
                new ResponseEntity(statisticResponse.get(), HttpStatus.OK);
    }
}

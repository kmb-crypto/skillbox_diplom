package main.controller;

import main.api.request.VoteRequest;
import main.service.VotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/post")
public class VotesController {
    private final VotesService votesService;

    @Autowired
    public VotesController(final VotesService votesService) {
        this.votesService = votesService;
    }

    @PostMapping(value = "/like")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity setLike(@RequestBody final VoteRequest voteRequest, final Principal principal) {
        return new ResponseEntity(votesService.getLikeResponse(voteRequest, principal), HttpStatus.OK);
    }
}

package com.imgarena.tennis_matches.endpoint;

import com.imgarena.tennis_matches.domain.FetchPurchasedMatches;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TennisMatchController {
    private final FetchPurchasedMatches fetchPurchasedMatches;

    public TennisMatchController(FetchPurchasedMatches fetchPurchasedMatches) {
        this.fetchPurchasedMatches = fetchPurchasedMatches;
    }

    @GetMapping(path = "/tennis-matches", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getUpcomingBettingEvents(
            @RequestHeader(name = "User-Id", required = false) String userId) {

        return ResponseEntity.ok(fetchPurchasedMatches.forCustomer(userId));
    }
}

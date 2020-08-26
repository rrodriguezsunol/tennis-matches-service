package com.imgarena.tennis_matches.endpoint;

import com.imgarena.tennis_matches.domain.FetchPurchasedMatches;
import com.imgarena.tennis_matches.dto.TennisMatchDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TennisMatchController {
    private final FetchPurchasedMatches fetchPurchasedMatches;

    public TennisMatchController(FetchPurchasedMatches fetchPurchasedMatches) {
        this.fetchPurchasedMatches = fetchPurchasedMatches;
    }

    @GetMapping(path = "/tennis-matches", produces = APPLICATION_JSON_VALUE)
    public Collection<TennisMatchDto> getUpcomingBettingEvents(
            @RequestHeader(name = "User-Id", required = false) String userId) {

        return fetchPurchasedMatches.forCustomer(userId);
    }
}

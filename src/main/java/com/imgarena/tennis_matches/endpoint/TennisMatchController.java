package com.imgarena.tennis_matches.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TennisMatchController {

    @GetMapping(path = "/tennis-matches", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getUpcomingBettingEvents(
            String userId,
            @RequestParam(required = false) @Nullable String purchaseStatus) {
        return ResponseEntity.ok("[]");
    }
}

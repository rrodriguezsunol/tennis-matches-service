package com.imgarena.tennis_matches.domain;

import com.imgarena.tennis_matches.dto.TennisMatchDto;
import com.imgarena.tennis_matches.persistence.TennisMatchPersistence;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FetchPurchasedMatches {
    private final TennisMatchPersistence tennisMatchPersistence;

    public FetchPurchasedMatches(TennisMatchPersistence tennisMatchPersistence) {
        this.tennisMatchPersistence = tennisMatchPersistence;
    }

    public Collection<TennisMatchDto> byCustomerId(String customerId) {
        return tennisMatchPersistence.findByCustomerBuyerId(customerId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private TennisMatchDto toDto(TennisMatch tennisMatch) {
        String playerAName = tennisMatch.getPlayerAName();
        String playerBName = tennisMatch.getPlayerBName();

        return new TennisMatchDto(
                tennisMatch.getId(),
                tennisMatch.getStartDateTime().atZone(ZoneOffset.UTC),
                playerAName,
                playerBName,
                String.format("%s vs %s", playerAName, playerBName));
    }
}

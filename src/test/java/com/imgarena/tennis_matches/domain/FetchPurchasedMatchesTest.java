package com.imgarena.tennis_matches.domain;

import com.imgarena.tennis_matches.dto.TennisMatchDto;
import com.imgarena.tennis_matches.persistence.TennisMatchPersistence;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class FetchPurchasedMatchesTest {

    // Test subject
    private FetchPurchasedMatches fetchPurchasedMatches;

    // Dependencies
    private TennisMatchPersistence mockedTennisMatchPersistence = Mockito.mock(TennisMatchPersistence.class);

    // Test data
    private String aCustomerId;


    @Before
    public void initTestSubject() {
        fetchPurchasedMatches = new FetchPurchasedMatches(mockedTennisMatchPersistence);
    }


    @Test
    public void returnsZeroMatchDtosWhenPersistenceReturnsZeroResults() {
        given(mockedTennisMatchPersistence.findByCustomerBuyerId(any())).willReturn(List.of());

        Collection<TennisMatchDto> foundMatches = fetchPurchasedMatches.byCustomerId(aCustomerId);

        assertThat(foundMatches).isEmpty();
    }

    @Test
    public void returnsOneMatchDtoWhenPersistenceReturnsOneTennisMatch() {
        LocalDateTime matchStartDateTime = LocalDateTime.parse("2020-07-15T18:00:00");

        given(mockedTennisMatchPersistence.findByCustomerBuyerId(any()))
                .willReturn(List.of(new TennisMatch(1, matchStartDateTime, "Rafael Nadal", "Roger Federer", "5678")));

        Collection<TennisMatchDto> foundMatches = fetchPurchasedMatches.byCustomerId("5678");

        assertThat(foundMatches).containsOnly(new TennisMatchDto(
                1,
                ZonedDateTime.parse("2020-07-15T18:00:00Z"),
                "Rafael Nadal",
                "Roger Federer",
                "Rafael Nadal vs Roger Federer"));
    }
}
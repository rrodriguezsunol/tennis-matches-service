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
    private String aCustomerId = "1234";


    @Before
    public void initTestSubject() {
        fetchPurchasedMatches = new FetchPurchasedMatches(mockedTennisMatchPersistence);
    }


    @Test
    public void returnsZeroMatchDtosWhenPersistenceReturnsZeroResults() {
        given(mockedTennisMatchPersistence.findSinglePurchases(any())).willReturn(List.of());

        Collection<TennisMatchDto> foundMatches = fetchPurchasedMatches.forCustomer(aCustomerId);

        assertThat(foundMatches).isEmpty();
    }

    @Test
    public void returnsOneMatchDtoWhenPersistenceReturnsOneTennisMatch() {
        given(mockedTennisMatchPersistence.findSinglePurchases("5678"))
                .willReturn(List.of(new TennisMatch(1, LocalDateTime.parse("2020-07-15T18:00:00"), "Rafael Nadal", "Roger Federer")));

        Collection<TennisMatchDto> foundMatches = fetchPurchasedMatches.forCustomer("5678");

        assertThat(foundMatches).containsOnly(
                new TennisMatchDto(
                        1,
                        ZonedDateTime.parse("2020-07-15T18:00:00Z"),
                        "Rafael Nadal",
                        "Roger Federer",
                        "Rafael Nadal vs Roger Federer"));
    }

    @Test
    public void returnsAllMatchDtosThatBelongToAPurchasedTournament() {
        given(mockedTennisMatchPersistence.findAllInTournamentPurchases("5678"))
                .willReturn(List.of(
                        new TennisMatch(1, LocalDateTime.parse("2020-10-23T12:00:00"), "Serena Williams", "Simona Halep"),
                        new TennisMatch(2, LocalDateTime.parse("2020-10-25T10:00:00"), "Andy Murray", "Novak Djokovic")
                ));

        Collection<TennisMatchDto> foundMatches = fetchPurchasedMatches.forCustomer("5678");

        assertThat(foundMatches).containsOnly(new TennisMatchDto(
                        1,
                        ZonedDateTime.parse("2020-10-23T12:00:00Z"),
                        "Serena Williams",
                        "Simona Halep",
                        "Serena Williams vs Simona Halep"),
                new TennisMatchDto(
                        2,
                        ZonedDateTime.parse("2020-10-25T10:00:00Z"),
                        "Andy Murray",
                        "Novak Djokovic",
                        "Andy Murray vs Novak Djokovic")
        );
    }

    @Test
    public void returnsBothSingleMatchPurchaseAsWellAsMatchesFromTournamentPurchased() {
        given(mockedTennisMatchPersistence.findSinglePurchases("5678"))
                .willReturn(List.of(new TennisMatch(1, LocalDateTime.parse("2020-07-15T18:00:00"), "Rafael Nadal", "Roger Federer")));

        given(mockedTennisMatchPersistence.findAllInTournamentPurchases("5678"))
                .willReturn(List.of(
                        new TennisMatch(2, LocalDateTime.parse("2020-10-23T12:00:00"), "Serena Williams", "Simona Halep"),
                        new TennisMatch(3, LocalDateTime.parse("2020-10-25T10:00:00"), "Andy Murray", "Novak Djokovic")
                ));

        Collection<TennisMatchDto> foundMatches = fetchPurchasedMatches.forCustomer("5678");

        assertThat(foundMatches).containsOnly(
                new TennisMatchDto(
                        1,
                        ZonedDateTime.parse("2020-07-15T18:00:00Z"),
                        "Rafael Nadal",
                        "Roger Federer",
                        "Rafael Nadal vs Roger Federer"),
                new TennisMatchDto(
                        2,
                        ZonedDateTime.parse("2020-10-23T12:00:00Z"),
                        "Serena Williams",
                        "Simona Halep",
                        "Serena Williams vs Simona Halep"),
                new TennisMatchDto(
                        3,
                        ZonedDateTime.parse("2020-10-25T10:00:00Z"),
                        "Andy Murray",
                        "Novak Djokovic",
                        "Andy Murray vs Novak Djokovic")
        );
    }
}
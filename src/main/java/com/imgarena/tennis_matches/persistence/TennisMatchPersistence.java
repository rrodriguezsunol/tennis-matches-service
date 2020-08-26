package com.imgarena.tennis_matches.persistence;

import com.imgarena.tennis_matches.domain.TennisMatch;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Collection;

public interface TennisMatchPersistence extends Repository<TennisMatch, Integer> {

    @Query("SELECT DISTINCT tm FROM TennisMatch tm, PurchasedLicense pl " +
            "WHERE pl.tennisMatch = tm AND pl.customerBuyerId = ?1")
    Collection<TennisMatch> findSinglePurchases(String customerId);

    @Query("SELECT DISTINCT tm FROM TennisMatch tm, PurchasedLicense pl, Tournament t " +
            "WHERE pl.tournament = t AND tm.tournament = t AND pl.customerBuyerId = ?1")
    Collection<TennisMatch> findAllInTournamentPurchases(String customerId);
}

package com.imgarena.tennis_matches.persistence;

import com.imgarena.tennis_matches.domain.TennisMatch;
import org.springframework.data.repository.Repository;

import java.util.Collection;

public interface TennisMatchPersistence extends Repository<TennisMatch, Integer> {

    Collection<TennisMatch> findByCustomerBuyerId(String customerId);
}

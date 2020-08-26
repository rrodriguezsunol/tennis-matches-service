package com.imgarena.tennis_matches.persistence;

import com.imgarena.tennis_matches.IntegrationCategory;
import com.imgarena.tennis_matches.domain.TennisMatch;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@Category(IntegrationCategory.class)
@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class TennisMatchPersistenceTest {

    @Autowired
    private TennisMatchPersistence tennisMatchPersistence;


    @Test
    public void findByCustomerBuyerIdReturnsZeroMatchesWhenTheCustomerHasNoPurchases() {
        var customerWithoutPurchases = "1234";

        Collection<TennisMatch> foundMatches = tennisMatchPersistence.findByCustomerBuyerId(customerWithoutPurchases);

        assertThat(foundMatches).isEmpty();
    }

    @Test
    public void findByCustomerBuyerReturnsOneMatchWhenTheCustomerHasPurchasedIt() {
        var customerWithOneMatchPurchased = "5678";

        Collection<TennisMatch> foundMatches = tennisMatchPersistence.findByCustomerBuyerId(customerWithOneMatchPurchased);

        assertThat(foundMatches).containsOnly(new TennisMatch(1, "Rafael Nadal", "Roger Federer", customerWithOneMatchPurchased));
    }
}

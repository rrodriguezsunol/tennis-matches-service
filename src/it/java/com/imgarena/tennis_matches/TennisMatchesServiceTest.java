package com.imgarena.tennis_matches;

import com.imgarena.tennis_matches.util.Resources;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

@Category(IntegrationCategory.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class TennisMatchesServiceTest {
    private static final String USER_ID_HEADER_KEY = "User-Id";
    private static final String TENNIS_MATCHES_PATH = "/tennis-matches";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void retrieveZeroLicensedMatchesForACustomerWithoutPurchases() {

        var requestEntity = RequestEntity.get(URI.create(TENNIS_MATCHES_PATH))
                .header(USER_ID_HEADER_KEY, "1234")
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThatJson(responseEntity.getBody()).isEqualTo("[]");
    }

    @Test
    public void retrieveOneLicensedMatchForACustomerThatHasPurchasedIt() {
        var requestEntity = RequestEntity.get(URI.create(TENNIS_MATCHES_PATH))
                .header(USER_ID_HEADER_KEY, "5678")
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThatJson(responseEntity.getBody()).isEqualTo(Resources.readFile("expectations/get-one-match.json"));
    }

    @Test
    public void aMatchCanBePurchasedByMultipleCustomers() {
        var requestEntity = RequestEntity.get(URI.create(TENNIS_MATCHES_PATH))
                .header(USER_ID_HEADER_KEY, "5678")
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThatJson(responseEntity.getBody()).isEqualTo(Resources.readFile("expectations/get-one-match.json"));

        requestEntity = RequestEntity.get(URI.create(TENNIS_MATCHES_PATH))
                .header(USER_ID_HEADER_KEY, "8736")
                .accept(MediaType.APPLICATION_JSON)
                .build();

        responseEntity = restTemplate.exchange(requestEntity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThatJson(responseEntity.getBody()).isEqualTo(Resources.readFile("expectations/get-one-match.json"));
    }

    @Test
    public void retrieveAllMatchesOfATournamentWhenTheCustomerBoughtTheEntireTournament() {
        var requestEntity = RequestEntity.get(URI.create(TENNIS_MATCHES_PATH))
                .header(USER_ID_HEADER_KEY, "2904")
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThatJson(responseEntity.getBody()).isEqualTo(Resources.readFile("expectations/all-us-open-matches.json"));
    }
}

package com.imgarena.tennis_matches.domain;

import java.util.Objects;

public class PurchasedLicense {
    private Integer id;
    private TennisMatch tennisMatch;
    private Tournament tournament;
    private String customerBuyerId;

    // JPA requirement
    protected PurchasedLicense() {}

    public Integer getId() {
        return id;
    }

    public TennisMatch getTennisMatch() {
        return tennisMatch;
    }

    public String getCustomerBuyerId() {
        return customerBuyerId;
    }

    public Tournament getTournament() {
        return tournament;
    }

    // Equals and Hashcode implementations that avoid fetching associated entities inadvertently
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchasedLicense that = (PurchasedLicense) o;

        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

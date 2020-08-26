package com.imgarena.tennis_matches.domain;

public class PurchasedLicense {
    private Integer id;
    private TennisMatch tennisMatch;
    private String customerBuyerId;

    // JPA requirement
    protected PurchasedLicense() {}

    public PurchasedLicense(Integer id, TennisMatch tennisMatch, String customerBuyerId) {
        this.id = id;
        this.tennisMatch = tennisMatch;
        this.customerBuyerId = customerBuyerId;
    }

    public Integer getId() {
        return id;
    }

    public TennisMatch getTennisMatch() {
        return tennisMatch;
    }

    public String getCustomerBuyerId() {
        return customerBuyerId;
    }
}

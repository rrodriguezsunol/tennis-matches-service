package com.imgarena.tennis_matches.domain;

import java.util.Objects;

public class TennisMatch {
    private Integer id;
    private String playerAName;
    private String playerBName;
    private String customerBuyerId;

    // JPA requirement
    protected TennisMatch() {}

    public TennisMatch(int id, String playerAName, String playerBName, String customerBuyerId) {
        this.id = id;
        this.playerAName = playerAName;
        this.playerBName = playerBName;
        this.customerBuyerId = customerBuyerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TennisMatch that = (TennisMatch) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(playerAName, that.playerAName) &&
                Objects.equals(playerBName, that.playerBName) &&
                Objects.equals(customerBuyerId, that.customerBuyerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, playerAName, playerBName, customerBuyerId);
    }

    @Override
    public String toString() {
        return "TennisMatch{" +
                "id=" + id +
                ", playerAName='" + playerAName + '\'' +
                ", playerBName='" + playerBName + '\'' +
                ", customerBuyerId='" + customerBuyerId + '\'' +
                '}';
    }
}

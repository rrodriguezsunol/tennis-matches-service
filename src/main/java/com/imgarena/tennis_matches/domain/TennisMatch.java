package com.imgarena.tennis_matches.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class TennisMatch {
    private Integer id;
    private LocalDateTime startDateTime;
    private String playerAName;
    private String playerBName;

    // JPA requirement
    protected TennisMatch() {}

    public TennisMatch(Integer id, LocalDateTime startDateTime, String playerAName, String playerBName) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.playerAName = playerAName;
        this.playerBName = playerBName;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public String getPlayerAName() {
        return playerAName;
    }

    public String getPlayerBName() {
        return playerBName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TennisMatch that = (TennisMatch) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(startDateTime, that.startDateTime) &&
                Objects.equals(playerAName, that.playerAName) &&
                Objects.equals(playerBName, that.playerBName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDateTime, playerAName, playerBName);
    }

    @Override
    public String toString() {
        return "TennisMatch{" +
                "id=" + id +
                ", startDateTime=" + startDateTime +
                ", playerAName='" + playerAName + '\'' +
                ", playerBName='" + playerBName + '\'' +
                '}';
    }
}

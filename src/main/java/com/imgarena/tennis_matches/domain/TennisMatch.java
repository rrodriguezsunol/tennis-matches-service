package com.imgarena.tennis_matches.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class TennisMatch {
    private Integer id;
    private LocalDateTime startDateTime;
    private String playerAName;
    private String playerBName;
    private Tournament tournament;

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

    public Tournament getTournament() {
        return tournament;
    }

    public String getSummary() {
        return String.format("%s vs %s", playerAName, playerBName);
    }

    // Equals and Hashcode implementations that avoid fetching associated entities inadvertently
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TennisMatch that = (TennisMatch) o;

        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

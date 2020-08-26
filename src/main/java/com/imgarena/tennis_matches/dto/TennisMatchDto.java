package com.imgarena.tennis_matches.dto;

import java.time.ZonedDateTime;
import java.util.Objects;

public final class TennisMatchDto {
    private final int matchId;
    private ZonedDateTime startDate;
    private final String playerA;
    private final String playerB;
    private final String summary;

    public TennisMatchDto(int matchId, ZonedDateTime startDate, String playerA, String playerB, String summary) {
        this.matchId = matchId;
        this.startDate = startDate;
        this.playerA = playerA;
        this.playerB = playerB;
        this.summary = summary;
    }

    public int getMatchId() {
        return matchId;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public String getPlayerA() {
        return playerA;
    }

    public String getPlayerB() {
        return playerB;
    }

    public String getSummary() {
        return summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TennisMatchDto that = (TennisMatchDto) o;
        return matchId == that.matchId &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(playerA, that.playerA) &&
                Objects.equals(playerB, that.playerB) &&
                Objects.equals(summary, that.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchId, startDate, playerA, playerB, summary);
    }

    @Override
    public String toString() {
        return "TennisMatchDto{" +
                "matchId=" + matchId +
                ", startDate=" + startDate +
                ", playerA='" + playerA + '\'' +
                ", playerB='" + playerB + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}

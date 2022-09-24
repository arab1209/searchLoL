package com.example.searchlol;

import android.util.Log;


//-------------------------------------자유랭크정보저장
public class freeRankData {
    private String tier;
    private String leaguePoints;
    private String wins;
    private String loses;
    private String rank;

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        Log.d("ftier", tier);
        this.tier = tier;
    }

    public String getLeaguePoints() {
        return leaguePoints;
    }

    public String getWins() {
        return wins;
    }

    public void setLeaguePoints(String leaguePoints) {
        Log.d("fleague", leaguePoints);
        this.leaguePoints = leaguePoints;
    }

    public void setWins(String wins) {
        Log.d("fwins", wins);
        this.wins = wins;
    }

    public String getLoses() {
        return loses;
    }

    public void setLoses(String loses) {
        Log.d("floses", loses);
        this.loses = loses;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        Log.d("frank", rank);
        this.rank = rank;
    }
}

package com.example.searchlol;

import android.util.Log;


//-----------------------유저아이디검색시 초기정보를 저장함
public class getInformation {
    private String tier;
    private String rank;
    private String leaguePoints;
    private String wins;
    private String losses;
    private String idLevel;
    private String profileIconId;
    private String puuid;


    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        Log.d("Tier", tier);
        this.tier = tier;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        Log.d("rank", rank);
        this.rank = rank;
    }

    public String getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(String leaguePoints) {
        Log.d("points", leaguePoints);
        this.leaguePoints = leaguePoints;
    }

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        Log.d("wins", wins);
        this.wins = wins;
    }

    public String getLosses() {
        return losses;
    }

    public void setLosses(String losses) {
        Log.d("losses", losses);
        this.losses = losses;
    }

    public String getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(String idLevel) {
        this.idLevel = idLevel;
    }

    public String getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(String profileIconId) {
        this.profileIconId = profileIconId;
    }


    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }
}

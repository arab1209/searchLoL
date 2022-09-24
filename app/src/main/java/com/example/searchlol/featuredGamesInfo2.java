package com.example.searchlol;

import android.graphics.Bitmap;
import android.util.Log;

// 진행중인 게임정보 레드팀 정보저장
public class featuredGamesInfo2 {
    private String userName, tier;
    private Bitmap bit, spell1Bit, spell2Bit, rune2Bit, rune1Bit;


    public featuredGamesInfo2(Bitmap bit, Bitmap spell1Bit, Bitmap spell2Bit, Bitmap rune1Bit, Bitmap rune2Bit, String userName, String tier) {
        this.bit = bit;
        this.spell1Bit = spell1Bit;
        this.spell2Bit = spell2Bit;
        this.rune1Bit = rune1Bit;
        this.rune2Bit = rune2Bit;
        this.userName = userName;
        this.tier = tier;
    }


    public Bitmap getBit() {
        return bit;
    }

    public Bitmap getSpell1Bit() {
        return spell1Bit;
    }

    public Bitmap getSpell2Bit() {
        return spell2Bit;
    }

    public Bitmap getRune2Bit() {
        return rune2Bit;
    }

    public Bitmap getRune1Bit() {
        return rune1Bit;
    }

    public String getUserName() {
        return userName;
    }

    public String getTier() {
        return tier;
    }
}

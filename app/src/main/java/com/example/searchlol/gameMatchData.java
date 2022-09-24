package com.example.searchlol;

import android.graphics.Bitmap;
import android.util.Log;


//----------------------------------게임중 matchId에 따라 게임정보를 저장하고 셋팅함
public class gameMatchData {
    private Bitmap championBit, spell1Bit, spell2Bit, rune2Bit, rune1Bit;
    private String queueType;
    private final boolean winsLosses;
    private Bitmap itemImg[] = new Bitmap[6];
    private String kda[] = new String[3];
    private String recyclerViewRight[] = new String[5];

    public gameMatchData(Bitmap championBit, Bitmap spell1Bit, Bitmap spell2Bit, Bitmap rune1Bit, Bitmap rune2Bit, Boolean winsLosseswhat, String queueType, Bitmap[] itemImg
    , String kda[], String recyclerViewRight[])
    {
        this.championBit = championBit;
        this.spell1Bit = spell1Bit;
        this.spell2Bit = spell2Bit;
        this.rune1Bit = rune1Bit;
        this.rune2Bit = rune2Bit;
        this.winsLosses = winsLosseswhat;
        this.queueType = queueType;
        for(int i=0; i<this.itemImg.length; i++)
        {
            Log.d("matchDataBitmap", String.valueOf(itemImg[i]));
            this.itemImg[i] = itemImg[i];
        }
        for(int i=0; i<this.kda.length; i++)
        {
            this.kda[i] = kda[i];
        }
        for(int i=0; i<this.recyclerViewRight.length; i++)
        {
            this.recyclerViewRight[i] = recyclerViewRight[i];
        }
    }
    public String[] getRecyclerViewRight()
    {
        return recyclerViewRight;
    }
    public String[] getKda()
    {
        return this.kda;
    }
    public boolean getWinsLosses()
    {
        return winsLosses;
    }
    public Bitmap getRune1Bit() {
        return rune1Bit;
    }
    public Bitmap getRune2Bit() {
        return rune2Bit;
    }
    public Bitmap getSpell2Bit() {
        return spell2Bit;
    }
    public Bitmap getSpell1Bit() {
        return spell1Bit;
    }
    public String getQueueType() {
        return queueType;
    }

    public Bitmap getChampionBit() {
        return championBit;
    }

    public Bitmap[] getItemImg() {
        return itemImg;
    }
}

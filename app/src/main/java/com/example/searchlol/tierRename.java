package com.example.searchlol;


//--------------------티어이름을 간략하게설정
public class tierRename {
    String tier;
    public tierRename(String tier)
    {
        this.tier = tier;
    }
    public String rename()
    {
        if(tier.equals("CHALLENGER I"))
        {
            tier = "C";
            return tier;
        }else if(tier.equals("GRANDMASTER I"))
        {
            tier = "GM";
            return tier;
        }else if(tier.equals("MASTER I"))
        {
            tier = "M";
            return tier;
        }else if(tier.equals("DIAMOND I"))
        {
            tier = "D1";
            return tier;
        }
        else if(tier.equals("DIAMOND II"))
        {
            tier = "D2";
            return tier;
        }
        else if(tier.equals("DIAMOND III"))
        {
            tier = "D3";
            return tier;
        }
        else if(tier.equals("DIAMOND IV"))
        {
            tier = "D4";
            return tier;
        }else if(tier.equals("PLATINUM I"))
        {
            tier = "P1";
            return tier;
        }else if(tier.equals("PLATINUM II"))
        {
            tier = "P2";
            return tier;
        }else if(tier.equals("PLATINUM III"))
        {
            tier = "P3";
            return tier;
        }else if(tier.equals("PLATINUM IV"))
        {
            tier = "P4";
            return tier;
        }else if(tier.equals("GOLD I"))
        {
            tier = "G1";
            return tier;
        }else if(tier.equals("GOLD II"))
        {
            tier = "G2";
            return tier;
        }else if(tier.equals("GOLD III"))
        {
            tier = "G3";
            return tier;
        }else if(tier.equals("GOLD IV"))
        {
            tier = "G4";
            return tier;
        }else if(tier.equals("SILVER I"))
        {
            tier = "S1";
            return tier;
        }else if(tier.equals("SILVER II"))
        {
            tier = "S2";
            return tier;
        }else if(tier.equals("SILVER III"))
        {
            tier = "S3";
            return tier;
        }else if(tier.equals("SILVER IV"))
        {
            tier = "S4";
            return tier;
        }else if(tier.equals("BRONZE I"))
        {
            tier = "B1";
            return tier;
        }else if(tier.equals("BRONZE II"))
        {
            tier = "B2";
            return tier;
        }else if(tier.equals("BRONZE III"))
        {
            tier = "B3";
            return tier;
        }else if(tier.equals("BRONZE IV"))
        {
            tier = "B4";
            return tier;
        }else if(tier.equals("IRON I"))
        {
            tier = "I1";
            return tier;
        }else if(tier.equals("IRON II"))
        {
            tier = "I2";
            return tier;
        }else if(tier.equals("IRON III"))
        {
            tier = "I3";
            return tier;
        }else if(tier.equals("IRON IV"))
        {
            tier = "I4";
            return tier;
        }
        return null;
    }
}

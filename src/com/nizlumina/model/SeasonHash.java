package com.nizlumina.model;

//Small class used for hashing to index. Feel free to roll your own.
public class SeasonHash
{
    private String seasonName;
    private String md5;

    public SeasonHash(String seasonName, String md5)
    {
        this.seasonName = seasonName;
        this.md5 = md5;
    }

    public String getSeasonName()
    {
        return seasonName;
    }

    public void setSeasonName(String seasonName)
    {
        this.seasonName = seasonName;
    }

    public String getMd5()
    {
        return md5;
    }

    public void setMd5(String md5)
    {
        this.md5 = md5;
    }
}

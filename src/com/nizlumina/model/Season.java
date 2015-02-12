package com.nizlumina.model;

//Small class used for hashing to index. Feel free to roll your own.
public class Season
{
    private String season;
    private int year;
    private String md5;

    public Season(String season, int year, String md5)
    {
        this.year = year;
        this.season = season;
        this.md5 = md5;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public String getSeason()
    {
        return season;
    }

    public void setSeason(String seasonName)
    {
        this.season = seasonName;
    }

    public String getMd5()
    {
        return md5;
    }

    public void setMd5(String md5)
    {
        this.md5 = md5;
    }

    public String getIndexKey()
    {
        return this.season + String.valueOf(year);
    }
}

package com.nizlumina.model.hummingbird.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Episode
{

    @Expose
    private int id;
    @Expose
    private String title;
    @Expose
    private String synopsis;
    @Expose
    private String airdate;
    @Expose
    private int number;
    @SerializedName("season_number")
    @Expose
    private int seasonNumber;

    /**
     * @return The id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return The title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return The synopsis
     */
    public String getSynopsis()
    {
        return synopsis;
    }

    /**
     * @param synopsis The synopsis
     */
    public void setSynopsis(String synopsis)
    {
        this.synopsis = synopsis;
    }

    /**
     * @return The airdate
     */
    public String getAirdate()
    {
        return airdate;
    }

    /**
     * @param airdate The airdate
     */
    public void setAirdate(String airdate)
    {
        this.airdate = airdate;
    }

    /**
     * @return The number
     */
    public int getNumber()
    {
        return number;
    }

    /**
     * @param number The number
     */
    public void setNumber(int number)
    {
        this.number = number;
    }

    /**
     * @return The seasonNumber
     */
    public int getSeasonNumber()
    {
        return seasonNumber;
    }

    /**
     * @param seasonNumber The season_number
     */
    public void setSeasonNumber(int seasonNumber)
    {
        this.seasonNumber = seasonNumber;
    }

}

package com.nizlumina.model.hummingbird.v2;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Titles
{

    @Expose
    private String canonical;
    @Expose
    private String english;
    @Expose
    private String romaji;
    @Expose
    private String japanese;

    /**
     * @return The canonical
     */
    public String getCanonical()
    {
        return canonical;
    }

    /**
     * @param canonical The canonical
     */
    public void setCanonical(String canonical)
    {
        this.canonical = canonical;
    }

    /**
     * @return The english
     */
    public String getEnglish()
    {
        return english;
    }

    /**
     * @param english The english
     */
    public void setEnglish(String english)
    {
        this.english = english;
    }

    /**
     * @return The romaji
     */
    public String getRomaji()
    {
        return romaji;
    }

    /**
     * @param romaji The romaji
     */
    public void setRomaji(String romaji)
    {
        this.romaji = romaji;
    }

    /**
     * @return The japanese
     */
    public String getJapanese()
    {
        return japanese;
    }

    /**
     * @param japanese The japanese
     */
    public void setJapanese(String japanese)
    {
        this.japanese = japanese;
    }

}

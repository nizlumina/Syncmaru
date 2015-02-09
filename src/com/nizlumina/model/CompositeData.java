package com.nizlumina.model;

import com.nizlumina.model.hummingbird.v2.AnimeObject;

/**
 * Composite data that wraps each subclasses
 */
public class CompositeData
{
    private int id;
    private MALObject malObject;
    private AnimeObject animeObject;
    private LiveChartObject aniChartObject;

    public CompositeData(int id, MALObject malObject, AnimeObject animeObject, LiveChartObject aniChartObject)
    {
        this.id = id;
        this.malObject = malObject;
        this.animeObject = animeObject;
        this.aniChartObject = aniChartObject;
    }

    public CompositeData()
    {
    }

    public LiveChartObject getAniChartObject()
    {
        return aniChartObject;
    }

    public void setAniChartObject(LiveChartObject aniChartObject)
    {
        this.aniChartObject = aniChartObject;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public MALObject getMalObject()
    {
        return malObject;
    }

    public void setMalObject(MALObject malObject)
    {
        this.malObject = malObject;
    }

    public AnimeObject getAnimeObject()
    {
        return animeObject;
    }

    public void setAnimeObject(AnimeObject animeObject)
    {
        this.animeObject = animeObject;
    }
}

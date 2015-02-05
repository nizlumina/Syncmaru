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
    private LiveChartObject liveChartObject;

    public CompositeData(int id, MALObject malObject, AnimeObject animeObject, LiveChartObject liveChartObject)
    {
        this.id = id;
        this.malObject = malObject;
        this.animeObject = animeObject;
        this.liveChartObject = liveChartObject;
    }

    public CompositeData()
    {
    }

    public LiveChartObject getLiveChartObject()
    {
        return liveChartObject;
    }

    public void setLiveChartObject(LiveChartObject liveChartObject)
    {
        this.liveChartObject = liveChartObject;
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

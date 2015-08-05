package com.nizlumina.model;

import com.nizlumina.model.hummingbird.v2.AnimeObject;

/**
 * Composite data that wraps each subclasses
 */
public class CompositeData
{
    private int id;
    private MALObject malObject;
    private SmallAnimeObject smallAnimeObject;
    private LiveChartObject liveChartObject;

    public CompositeData(int id, MALObject malObject, AnimeObject animeObject, LiveChartObject aniChartObject)
    {
        this.id = id;
        this.malObject = malObject;
        this.smallAnimeObject = new SmallAnimeObject(animeObject);
        this.liveChartObject = aniChartObject;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

}

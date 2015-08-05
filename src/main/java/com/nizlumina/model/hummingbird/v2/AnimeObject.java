package com.nizlumina.model.hummingbird.v2;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AnimeObject
{

    @Expose
    private Anime anime;
    @Expose
    private Linked linked;

    /**
     * @return The anime
     */
    public Anime getAnime()
    {
        return anime;
    }

    /**
     * @param anime The anime
     */
    public void setAnime(Anime anime)
    {
        this.anime = anime;
    }

    /**
     * @return The linked
     */
    public Linked getLinked()
    {
        return linked;
    }

    /**
     * @param linked The linked
     */
    public void setLinked(Linked linked)
    {
        this.linked = linked;
    }

}

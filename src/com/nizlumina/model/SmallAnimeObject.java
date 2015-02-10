package com.nizlumina.model;

import com.nizlumina.model.hummingbird.v2.AnimeObject;

public class SmallAnimeObject
{

    int id;
    String slug;
    String showType;
    String posterImage;

    //An optional constructor needed for transferring Hummingbird AnimeObject (v2) to this model.
    //Feel free to create your own
    public SmallAnimeObject(AnimeObject animeObject)
    {
        this.id = animeObject.getAnime().getId();
        this.slug = animeObject.getAnime().getSlug();
        this.showType = animeObject.getAnime().getShowType();
        this.posterImage = animeObject.getAnime().getPosterImage();
    }
}

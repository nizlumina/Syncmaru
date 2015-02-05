package com.nizlumina.model.hummingbird.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Links
{

    @SerializedName("gallery_images")
    @Expose
    private List<Integer> galleryImages = new ArrayList<Integer>();
    @Expose
    private List<Integer> episodes = new ArrayList<Integer>();

    /**
     * @return The galleryImages
     */
    public List<Integer> getGalleryImages()
    {
        return galleryImages;
    }

    /**
     * @param galleryImages The gallery_images
     */
    public void setGalleryImages(List<Integer> galleryImages)
    {
        this.galleryImages = galleryImages;
    }

    /**
     * @return The episodes
     */
    public List<Integer> getEpisodes()
    {
        return episodes;
    }

    /**
     * @param episodes The episodes
     */
    public void setEpisodes(List<Integer> episodes)
    {
        this.episodes = episodes;
    }

}

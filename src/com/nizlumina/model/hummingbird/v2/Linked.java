package com.nizlumina.model.hummingbird.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Linked
{

    @SerializedName("gallery_images")
    @Expose
    private List<GalleryImage> galleryImages = new ArrayList<GalleryImage>();
    @Expose
    private List<Episode> episodes = new ArrayList<Episode>();

    /**
     * @return The galleryImages
     */
    public List<GalleryImage> getGalleryImages()
    {
        return galleryImages;
    }

    /**
     * @param galleryImages The gallery_images
     */
    public void setGalleryImages(List<GalleryImage> galleryImages)
    {
        this.galleryImages = galleryImages;
    }

    /**
     * @return The episodes
     */
    public List<Episode> getEpisodes()
    {
        return episodes;
    }

    /**
     * @param episodes The episodes
     */
    public void setEpisodes(List<Episode> episodes)
    {
        this.episodes = episodes;
    }

}

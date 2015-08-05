package com.nizlumina.model.hummingbird.v2;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class GalleryImage
{

    @Expose
    private int id;
    @Expose
    private String thumb;
    @Expose
    private String original;

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
     * @return The thumb
     */
    public String getThumb()
    {
        return thumb;
    }

    /**
     * @param thumb The thumb
     */
    public void setThumb(String thumb)
    {
        this.thumb = thumb;
    }

    /**
     * @return The original
     */
    public String getOriginal()
    {
        return original;
    }

    /**
     * @param original The original
     */
    public void setOriginal(String original)
    {
        this.original = original;
    }

}

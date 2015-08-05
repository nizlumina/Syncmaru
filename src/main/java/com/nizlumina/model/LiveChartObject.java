package com.nizlumina.model;

import java.util.List;


public class LiveChartObject
{
    String title;
    String studio;
    Category category; //1: TV series, 2: Movies 3: Specials (OVA/ONA)
    String source; //Only used for category 1:
    String type; //Only used for ONA/OVA. Airing anime doesn't have types
    String malLink;
    String malID;
    String hummingbirdLink;
    String hummingbirdSlug;
    String crunchyrollLink;
    String website;

    public LiveChartObject(String title, String studio, Category category, String source, String type, List<String> links)
    {
        this.title = title;
        this.studio = studio;
        this.category = category;
        this.source = source;
        this.type = type;
    }

    public LiveChartObject() {}

    public String getMalLink()
    {
        return malLink;
    }

    public void setMalLink(String malLink)
    {
        this.malLink = malLink;
    }

    public String getMalID()
    {
        return malID;
    }

    public void setMalID(String malID)
    {
        this.malID = malID;
    }

    public String getHummingbirdLink()
    {
        return hummingbirdLink;
    }

    public void setHummingbirdLink(String hummingbirdLink)
    {
        this.hummingbirdLink = hummingbirdLink;
    }

    public String getHummingbirdSlug()
    {
        return hummingbirdSlug;
    }

    public void setHummingbirdSlug(String hummingbirdSlug)
    {
        this.hummingbirdSlug = hummingbirdSlug;
    }

    public String getCrunchyrollLink()
    {
        return crunchyrollLink;
    }

    public void setCrunchyrollLink(String crunchyrollLink)
    {
        this.crunchyrollLink = crunchyrollLink;
    }

    public String getWebsite()
    {
        return website;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getStudio()
    {
        return studio;
    }

    public void setStudio(String studio)
    {
        this.studio = studio;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public void setCategory(String categoryOrdinals)
    {
        this.category = Category.getCategory(Integer.parseInt(categoryOrdinals));
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public enum Category
    {
        TV, MOVIE, SPECIAL;

        public static Category getCategory(int ordinals)
        {
            for (Category category : Category.values())
                if (category.ordinal() == ordinals - 1)
                    return category; //since livechart type index starts from 1.
            return null;
        }
    }


}

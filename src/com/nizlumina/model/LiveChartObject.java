package com.nizlumina.model;

public class LiveChartObject
{
    String id;
    String name;
    String link;
    String studio;
    String source;

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getStudio()
    {
        return studio;
    }

    public void setStudio(String studio)
    {
        this.studio = studio;
    }

    public String getLoggingData()
    {
        return String.format("LogData:\nID: %s\nName: %s\nSource: %s\nStudio: %s\nLink: %s\n",
                getId(), getName(), getSource(), getStudio(), getLink());
    }
}

package com.nizlumina.model;

import java.util.ArrayList;
import java.util.List;

public class LiveChartObject
{
    String title;
    String studio;
    String source; //Only used for airing anime
    String type; //Only used for ONA/OVA. Airing anime doesn't have types
    List<String> links;

    public LiveChartObject() {}

    public LiveChartObject(String title, String studio, String source, String type, String links)
    {
        this.title = title;
        this.studio = studio;
        this.source = source;
        this.type = type;
        this.links = new ArrayList<String>();
    }

    public boolean isAiringAnime()
    {
        return type == null;
    }
}

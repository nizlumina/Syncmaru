package com.nizlumina.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nizlumina.model.hummingbird.v2.AnimeObject;

public class HummingbirdJSONFactory
{
    public static String toJSONString(AnimeObject animeObject)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(animeObject);
    }

    public static AnimeObject fromJSON(String jsonString)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.fromJson(jsonString, AnimeObject.class);
    }
}

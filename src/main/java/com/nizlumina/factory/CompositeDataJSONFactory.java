package com.nizlumina.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nizlumina.model.CompositeData;

import java.util.List;

public class CompositeDataJSONFactory
{
    public static String toJSON(CompositeData compositeData)
    {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        return gson.toJson(compositeData);
    }

    public static String listToJSON(List<CompositeData> compositeDatas)
    {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        return gson.toJson(compositeDatas, new TypeToken<List<CompositeData>>() {}.getType());
    }

    public static CompositeData fromJSON(String jsonString)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.fromJson(jsonString, CompositeData.class);
    }

    public static List<CompositeData> listFromJSON(String jsonString)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.fromJson(jsonString, new TypeToken<List<CompositeData>>() {}.getType());
    }
}

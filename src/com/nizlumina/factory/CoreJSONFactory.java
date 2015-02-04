package com.nizlumina.factory;


import com.nizlumina.model.AnimeObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Static factory for saving and loading Core data.<br/>
 * JSONObject methods is returned as an empty object upon exception.<br/>
 * JSONArray and other collections methods omitted failures and returns data that were only successful.<br/><br/>
 * NyaaEntry and AnimeObject methods returns NULL on ANY failure due to the higher integrity needed (do null check if you call their methods).
 */
public class CoreJSONFactory
{

    public static AnimeObject animeObjectFromJSON(JSONObject jsonObject, boolean strictHummingbirdJSON)
    {
        if (jsonObject == null) return null;
        AnimeObject animeObject = new AnimeObject();
        animeObject.id = jsonObject.optInt(AnimeObject.JSON_API_ID);
        animeObject.slug = jsonObject.optString(AnimeObject.JSON_API_SLUG);
        animeObject.status = jsonObject.optString(AnimeObject.JSON_API_STATUS);
        animeObject.url = jsonObject.optString(AnimeObject.JSON_API_URL);
        animeObject.title = jsonObject.optString(AnimeObject.JSON_API_TITLE);
        animeObject.episodeCount = jsonObject.optInt(AnimeObject.JSON_API_EPS_COUNT);
        animeObject.imageUrl = jsonObject.optString(AnimeObject.JSON_API_COVER_IMG_URL);
        animeObject.synopsis = jsonObject.optString(AnimeObject.JSON_API_SYNOPSIS);
        animeObject.startedAiring = jsonObject.optString(AnimeObject.JSON_API_STARTED_AIRING);
        animeObject.finishedAiring = jsonObject.optString(AnimeObject.JSON_API_FINISHED_AIRING);

        if (!strictHummingbirdJSON)
            animeObject.cachedImageURI = jsonObject.optString(AnimeObject.JSON_CACHED_IMG_URI);
        return animeObject;

    }

    public static List<AnimeObject> animeObjectsFromJSON(JSONArray jsonArray, boolean strictHummingbirdJSON)
    {
        List<AnimeObject> results = new ArrayList<AnimeObject>();
        for (int i = 0; i < jsonArray.length(); i++)
        {
            try
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject != null)
                {
                    AnimeObject animeObject = animeObjectFromJSON(jsonObject, strictHummingbirdJSON);
                    if (animeObject != null)
                        results.add(animeObject);
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return results;
    }

    public static JSONObject toJSON(AnimeObject animeObject, boolean strictHummingbirdJSON)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.putOpt(AnimeObject.JSON_API_ID, animeObject.id)
                    .putOpt(AnimeObject.JSON_API_SLUG, animeObject.slug)
                    .putOpt(AnimeObject.JSON_API_STATUS, animeObject.status)
                    .putOpt(AnimeObject.JSON_API_URL, animeObject.url)
                    .putOpt(AnimeObject.JSON_API_TITLE, animeObject.title)
                    .putOpt(AnimeObject.JSON_API_EPS_COUNT, animeObject.episodeCount)
                    .putOpt(AnimeObject.JSON_API_COVER_IMG_URL, animeObject.imageUrl)
                    .putOpt(AnimeObject.JSON_API_SYNOPSIS, animeObject.synopsis)
                    .putOpt(AnimeObject.JSON_API_STARTED_AIRING, animeObject.startedAiring)
                    .putOpt(AnimeObject.JSON_API_FINISHED_AIRING, animeObject.finishedAiring);

            if (!strictHummingbirdJSON)
                jsonObject.put(AnimeObject.JSON_CACHED_IMG_URI, animeObject.cachedImageURI);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
        return jsonObject;
    }

    public static JSONArray toJSONArray(List<AnimeObject> animeObjects, boolean strictHummingbirdJSON)
    {
        JSONArray results = new JSONArray();
        if (animeObjects != null && animeObjects.size() > 0)
        {
            for (AnimeObject animeObject : animeObjects)
            {
                results.put(toJSON(animeObject, strictHummingbirdJSON));

            }
        }
        return results;
    }

}

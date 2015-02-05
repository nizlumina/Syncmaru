package com.nizlumina.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nizlumina.model.MALObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.List;

public class MALJSONFactory
{

    public static String toJSONString(MALObject malObject)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(malObject);
    }

    public static MALObject malObjectFromJSON(String jsonString)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.fromJson(jsonString, MALObject.class);
    }

    //This actually works. Check the sample_mal_converted in the raw folder
    public static List<MALObject> fromXML(String malXML)
    {
        List<MALObject> output = new ArrayList<MALObject>();
        try
        {
            //Test
            //String sample = IOUtils.toString(new FileInputStream(new File("raw/sample_mal.xml")));
            //JSONObject jsonObject = XML.toJSONObject(sample);
            //IOUtils.write(jsonObject.toString(), new FileOutputStream(new File("yolo.json")));

            JSONObject jsonObject = XML.toJSONObject(malXML);
            JSONObject identifier = jsonObject.getJSONObject(MALObject.IDENTIFIER);

            try
            {
                //test if results is an array of entry
                JSONArray array = identifier.getJSONArray(MALObject.ENTRY_ARRAY_IDENTIFIER);
                for (int i = 0; i < array.length(); i++)
                {
                    JSONObject object = array.getJSONObject(i);
                    MALObject malObject = malObjectFromJSON(object.toString());
                    if (malObject != null) output.add(malObject);
                }
            }
            catch (JSONException e)
            {
                try
                {
                    //test if results is only an entry
                    JSONObject object = identifier.getJSONObject(MALObject.ENTRY_ARRAY_IDENTIFIER);
                    MALObject malObject = malObjectFromJSON(object.toString());
                    if (malObject != null) output.add(malObject);
                }
                catch (JSONException e1)
                {
                    e.printStackTrace();
                    e1.printStackTrace();
                }
            }
            return output;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

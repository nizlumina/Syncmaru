package com.nizlumina.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nizlumina.model.MALObject;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by archi_000 on 2/4/2015.
 */
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

//        try
//        {
//            return new MALObject.Builder()
//                    .setId(jsonObject.getInt("id"))
//                    .setTitle(jsonObject.getString("title"))
//                    .setEnglish(jsonObject.getString("english"))
//                    .setSynonyms(jsonObject.getString("synonym"))
//                    .setEpisodes(jsonObject.getInt("episodes"))
//                    .setScore((float) jsonObject.getDouble("score"))
//                    .setType(jsonObject.getString("type"))
//                    .setStatus(jsonObject.getString("status"))
//                    .setStartDate(jsonObject.getString("start_date"))
//                    .setEndDate(jsonObject.getString("end_date"))
//                    .setSynopsis(jsonObject.getString("synopsis"))
//                    .setImage(jsonObject.getString("image"))
//                    .createMALObject();
//        }
//        catch (JSONException e){
//            e.printStackTrace();
//        }
    }

    //This actually works. Check the sample_mal_converted in the raw folder
    public static List<MALObject> fromXML(String malXML)
    {
        List<MALObject> output = new ArrayList<MALObject>();
        try
        {
            //Test
            String sample = IOUtils.toString(new FileInputStream(new File("raw/sample_mal.xml")));
            JSONObject jsonObject = XML.toJSONObject(sample);
            IOUtils.write(jsonObject.toString(), new FileOutputStream(new File("yolo.txt")));

            //JSONObject jsonObject = XML.toJSONObject(malXML);
            JSONObject identifier = jsonObject.getJSONObject(MALObject.IDENTIFIER);
            JSONArray array = identifier.getJSONArray(MALObject.ENTRY_ARRAY_IDENTIFIER);
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject object = array.getJSONObject(i);
                MALObject malObject = malObjectFromJSON(object.toString());
                if (malObject != null) output.add(malObject);
            }

            return output;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

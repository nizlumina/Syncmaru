package com.nizlumina.utils;

import com.nizlumina.Syncmaru;
import com.nizlumina.factory.MALJSONFactory;
import com.nizlumina.factory.MALQueryFactory;
import com.nizlumina.model.LiveChartObject;
import com.nizlumina.model.MALObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SyncmaruProcessor
{
    private final WebUnit mWebUnit;
    private final List<MALObject> malObjectsResult;


    public SyncmaruProcessor()
    {
        mWebUnit = new WebUnit();
        mWebUnit.setUserAgent(Syncmaru.MAL_USERAGENT);
        malObjectsResult = new ArrayList<MALObject>();
    }

    public void processLinkage(List<LiveChartObject> liveChartObjects)
    {
        for (LiveChartObject liveChartObject : liveChartObjects)
        {
            int id = Integer.parseInt(liveChartObject.getId().trim());
            processMalResults(id, liveChartObject.getName());
        }

        processHummingbirdData();
    }

    private void processMalResults(int id, String title)
    {
        String xmlString = null;
        try
        {
            //title count limit here
            xmlString = mWebUnit.getString(MALQueryFactory.buildQuery(title, 3));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if (xmlString != null)
        {
            List<MALObject> malObjects = MALJSONFactory.fromXML(xmlString);
            for (MALObject malObject : malObjects)
            {
                if (malObject.getId() == id)
                {
                    malObjectsResult.add(malObject);
                }
            }
        }
    }

    private void processHummingbirdData()
    {

    }

}

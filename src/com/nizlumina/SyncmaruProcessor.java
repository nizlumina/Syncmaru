package com.nizlumina;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nizlumina.factory.CompositeDataJSONFactory;
import com.nizlumina.factory.MALJSONFactory;
import com.nizlumina.factory.QueryFactory;
import com.nizlumina.model.CompositeData;
import com.nizlumina.model.LiveChartObject;
import com.nizlumina.model.MALObject;
import com.nizlumina.model.hummingbird.v2.AnimeObject;
import com.nizlumina.scraper.ChartScraper;
import com.nizlumina.utils.WebUnit;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class SyncmaruProcessor
{
    private final WebUnit malWebUnit, mHummWebUnit;
    private final List<LiveChartObject> mLiveChartObjects;
    private final Map<LiveChartObject, MALObject> mMalChartObjectsResult;
    private final List<CompositeData> mCompositeDatas;
    private final ChartScraper.Season mSeason;
    private final int mYear;
    int iLimit = 0;
    private boolean logging = true;
    private int debugLimit = 1;
    private List<LiveChartObject> mFailures;

    public SyncmaruProcessor(List<LiveChartObject> liveChartObjects, ChartScraper.Season season, int year)
    {
        mLiveChartObjects = liveChartObjects;
        mYear = year;
        mSeason = season;

        malWebUnit = new WebUnit();
        malWebUnit.setUserAgent(Syncmaru.MAL_USERAGENT);
        malWebUnit.getClient().setConnectTimeout(15, TimeUnit.SECONDS); //speed is king!
        malWebUnit.getClient().setReadTimeout(15, TimeUnit.SECONDS); //speed is king!

        malWebUnit.setCredentials(Syncmaru.MAL_USERNAME, Syncmaru.MAL_PASSWORD);

        mHummWebUnit = new WebUnit();
        mHummWebUnit.setUserAgent(Syncmaru.HUMMINGBIRD_USERAGENT);
        mHummWebUnit.addHeaders("X-Client-Id", Syncmaru.HUMMINGBIRD_CLIENTID);

        mMalChartObjectsResult = new HashMap<LiveChartObject, MALObject>(0);
        mCompositeDatas = new ArrayList<CompositeData>();
    }

    public void processLinkage()
    {
        processLiveChartObject();
        processHummingbirdData();
        saveLocal();
        uploadToFireBase();
    }

    private void processLiveChartObject()
    {

        mFailures = new ArrayList<LiveChartObject>();
        for (LiveChartObject liveChartObject : mLiveChartObjects)
        {
            //if (iLimit++ > debugLimit) break;

            int id = Integer.parseInt(liveChartObject.getId().trim());
            boolean success = processMalResults(liveChartObject, liveChartObject.getName(), 1, -1);
            if (!success) mFailures.add(liveChartObject);
        }
        log("CHART PROCESSED. FAILURE COUNT: " + mFailures.size());


        log("\n\nPROCESSING CHART WITH 2ND STRATEGY\n\n");
        Iterator<LiveChartObject> failureIterator = mFailures.iterator();
        while (failureIterator.hasNext())
        {
            LiveChartObject failureObject = failureIterator.next();
            boolean success = processMalResults(failureObject, failureObject.getName(), 2, -1); //try with 2
            if (success) failureIterator.remove();
        }

        while (failureIterator.hasNext())
        {
            LiveChartObject failureObject = failureIterator.next();
            boolean success = processMalResults(failureObject, failureObject.getName(), 3, -1); //try with 3
            if (success) failureIterator.remove();
        }

        Scanner scanner = new Scanner(System.in);
        for (LiveChartObject liveChartObject : mFailures)
        {
            boolean success = false;
            int checkID = Integer.parseInt(liveChartObject.getId());
            while (!success)
            {
                log("\n\nFAILED:\n" + liveChartObject.getLoggingData());

                log("\nPlease input the right terms for : " + liveChartObject.getName());
                String keyboardInput = scanner.nextLine();

                log("Correctional searching with [" + keyboardInput + "]");


                success = processMalResults(liveChartObject, keyboardInput, 5, checkID);

                if (!success)
                {
                    log("Failed again. Type S to skip or an ID number from above (to coerce to the found MAL ID) or press anything else to retry:");
                    String kbInput = scanner.nextLine();

                    int intInput = -1;
                    try
                    {
                        intInput = Integer.parseInt(kbInput.trim());
                    }
                    catch (Exception e)
                    {

                    }

                    if (kbInput.equalsIgnoreCase("S"))
                    {
                        log(liveChartObject.getId() + " skipped");
                        break;
                    }
                    else if (intInput > 0)
                    {
                        checkID = intInput;
                        log("\n\nRetrying with the ID: " + intInput);

                        success = processMalResults(liveChartObject, keyboardInput, 5, checkID);
                    }
                }
            }
        }
    }


    private boolean processMalResults(LiveChartObject liveChartObject, String title, int termsWordLimit, int idOverride)
    {
        String xmlString = null;

        int id;
        if (idOverride >= 0) id = idOverride;
        else id = Integer.parseInt(liveChartObject.getId());

        String url = QueryFactory.buildMALSearchQuery(title, termsWordLimit);
        log("\nProcessing MAL ID: " + id + " " + title);
        log(url);
        try
        {
            xmlString = malWebUnit.getString(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        final String logObject = "[" + liveChartObject.getId() + "] " + title + " [" + url + "]";

        if (xmlString != null)
        {
            List<MALObject> malObjects = MALJSONFactory.fromXML(xmlString);
            if (malObjects != null)
            {
                log("MAL Object Search Result: " + malObjects.size());

                for (MALObject malObject : malObjects)
                {
                    log("Comparing[" + malObject.getId() + "] " + malObject.getTitle() + " to [" + id + "]");
                    if (malObject.getId() == id)
                    {
                        mMalChartObjectsResult.put(liveChartObject, malObject);
                        log("\nChart-to-MAL match SUCCESS: " + malObject.getId());
                        return true;
                    }
                }


                log("ERROR: MAL SEARCH CAN'T FIND: " + logObject);
            }
            else log("ERROR: MAL OBJECTS IS NULL FOR: " + logObject);
        }
        else log("ERROR: NULL XML FOR MAL SEARCH: " + logObject);
        log("\nAdded to failures. Current FAILED size: " + mFailures.size());

        return false;
    }

    private void processHummingbirdData()
    {
        log("\nProcessing Hummingbird Data\n");
        List<Map.Entry<LiveChartObject, MALObject>> failuresList = new ArrayList<Map.Entry<LiveChartObject, MALObject>>(0);

        for (Map.Entry<LiveChartObject, MALObject> malChartObjectEntry : mMalChartObjectsResult.entrySet())
        {
            String jsonString = null;
            final MALObject malObject = malChartObjectEntry.getValue();
            try
            {
                log("Getting Hummingbird response for: " + malObject.getTitle() + " [" + malObject.getId() + "]");
                String url = QueryFactory.buildHummingbirdGetQuery(String.valueOf(malObject.getId()));
                log(url);
                jsonString = mHummWebUnit.getString(url);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            boolean success = false;
            if (jsonString != null)
            {
                Gson gson = new GsonBuilder().serializeNulls().create();

                AnimeObject animeObject = gson.fromJson(jsonString, AnimeObject.class);
                if (animeObject != null)
                {
                    success = true;
                    mCompositeDatas.add(new CompositeData(malObject.getId(), malObject, animeObject, malChartObjectEntry.getKey()));
                    log(animeObject.getAnime().getSlug() + " successfully added\n");
                }
                else
                    log("ERROR: Hummingbird from JSON failure:" + malObject.getTitle() + " [" + malObject.getId() + "]");
            }
            else
                log("ERROR: Hummingbird response (null JSON) failure:" + malObject.getTitle() + " [" + malObject.getId() + "]");

            if (!success)
            {
                failuresList.add(malChartObjectEntry);
            }
        }

        log("\nProcessing Hummingbird failures\n");

        Iterator<Map.Entry<LiveChartObject, MALObject>> failureIterator = failuresList.iterator();
        Scanner kbInput = new Scanner(System.in);
        while (failureIterator.hasNext())
        {
            final Map.Entry<LiveChartObject, MALObject> failureObject = failureIterator.next();
            final LiveChartObject liveChartObject = failureObject.getKey();
            final MALObject malObject = failureObject.getValue();

            log("Failed pair:" + liveChartObject.getLoggingData() + "\n\nMAL:" + malObject.getTitle() + "\nID[" + malObject.getId() + "]");

            log("\n Press S to skip adding to final data or P to proceed with a null Hummingbird object");

            String readInput = kbInput.nextLine();
            if (readInput.equalsIgnoreCase("S"))
            {
                failureIterator.remove();
                log("Skipped[" + malObject.getId() + "]" + malObject.getTitle());
            }
            else if (readInput.equalsIgnoreCase("P"))
            {
                failureIterator.remove();
                mCompositeDatas.add(new CompositeData(malObject.getId(), malObject, null, liveChartObject));
                log("Added to main set with null hummingbird object");
            }
        }
    }

    private void saveLocal()
    {
        String jsonString = CompositeDataJSONFactory.listToJSON(mCompositeDatas);
        // /year/season
        try
        {
            IOUtils.write(jsonString, new FileOutputStream(new File(mSeason.name() + mYear + ".json")));
            String saved = mCompositeDatas.size() + " objects saved!";
            log(saved);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void uploadToFireBase()
    {

    }

    private void log(String logString)
    {
        if (logging) System.out.println(logString);
    }

}

package com.nizlumina.factory;

import com.nizlumina.model.LiveChartObject;
import com.nizlumina.scraper.LiveChartScraper;
import com.nizlumina.utils.WebUnit;

import java.io.IOException;
import java.util.List;

public class LiveChartWebFactory
{
    public static String ENDPOINT = "https://livechart.me/";

    public static List<LiveChartObject> requestChart(Season season, String year)
    {
        try
        {
            WebUnit mLiveChartWebUnit = new WebUnit();

            String requestEndpoint = makeEndpoint(season, year);
            System.out.println(requestEndpoint);
            String html = mLiveChartWebUnit.getString(requestEndpoint);

            LiveChartScraper scraper = new LiveChartScraper();
            return scraper.scrape(html);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static String makeEndpoint(Season season, String year)
    {
        return ENDPOINT + season.name() + "-" + year;
    }

    public enum Season
    {
        spring, summer, fall, winter //lowercase due to easier URL matching
    }
}

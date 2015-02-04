package com.nizlumina.scraper;

import com.nizlumina.model.LiveChartObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChartScraper
{
    //We only run this once a month. Promise.
    private static final String ENDPOINT = "https://livechart.me/";
    private static final String OBJECT_SELECTOR = ".card.ng-scope";
    private static final String INNER_MAL = "thumb-link";
    private static final String INNER_TITLE = "card__title";
    private static final String INNER_STUDIO = "card__studio";
    private static final String INNER_SOURCE = "info_box", INNER_SOURCE_STRING = "Source";
    private boolean logging = false;
    private Season mScrapedSeason;

    public Season getScrapedSeason()
    {
        return mScrapedSeason;
    }

    public void setLogging(boolean enabled)
    {
        logging = enabled;
    }

    public List<LiveChartObject> scrapeData(File htmlFile)
    {
        try
        {
            return process(Jsoup.parse(htmlFile, "UTF-8"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private List<LiveChartObject> process(Document document)
    {
        List<LiveChartObject> output = new ArrayList<LiveChartObject>();
        mScrapedSeason = Season.getSeason(document.title());
        System.out.print("Current season: " + mScrapedSeason.name());

        //Get each main card
        Elements cardElements = document.select(OBJECT_SELECTOR);
        System.out.println("\nElement Size: " + cardElements.size());

        int index = 0;
        for (Element card : cardElements)
        {
            LiveChartObject liveChartObject = new LiveChartObject();

            //Get MAL
            Elements malClassRaws = card.getElementsByClass(INNER_MAL);
            if (malClassRaws.size() > 0)
            {
                //first index of the element contains a link to MAL
                String malLink = malClassRaws.get(0).attr("href");
                if (logging)
                    System.out.println("\nCard index: " + index++);
                System.out.println(malLink);

                liveChartObject.setLink(malLink);
                liveChartObject.setId(parseMALId(malLink));
            }

            //Get title (though title in the database will still follow Hummingbird/MAL)
            Elements cardTitleRaws = card.getElementsByClass(INNER_TITLE);
            for (Element cardTitleRaw : cardTitleRaws)
            {
                liveChartObject.setName(cardTitleRaw.child(0).ownText());
            }

            //Get studio
            Elements cardStudioRaws = card.getElementsByClass(INNER_STUDIO);
            for (Element cardStudio : cardStudioRaws)
            {
                liveChartObject.setStudio(cardStudio.child(0).ownText());
            }

            //Get source type
            Elements infoBoxesRaws = card.getElementsByClass(INNER_SOURCE);
            for (Element infoBox : infoBoxesRaws)
            {
                String innerValue = infoBox.ownText();
                if (innerValue.equalsIgnoreCase(INNER_SOURCE_STRING))
                {
                    liveChartObject.setSource(infoBox.child(0).ownText());
                    break;
                }
            }

            if (logging) System.out.println("\n" + liveChartObject.getLoggingData());

            output.add(liveChartObject);
        }
        return output;
    }

    public String parseMALId(final String urlString)
    {
        try
        {
            final URL url = new URL(urlString);
            final String[] splittedString = url.getPath().trim().split("/");

            final int secondLastIndex = Math.abs(splittedString.length - 2);
            if (logging)
            {
                System.out.println(String.format("Path:[%s]", url.getPath()));
                for (String string : splittedString)
                {
                    System.out.print(String.format("[%s] ", string));
                }
                System.out.println("\nParsedID [" + splittedString[secondLastIndex] + "]");
            }
            return splittedString[secondLastIndex];

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public enum Season
    {
        //we'll match this to the url as well via Enum.getName()
        SPRING, SUMMER, FALL, WINTER;

        public static Season getSeason(String victim)
        {
            for (Season season : Season.values())
            {
                if (victim.toUpperCase().contains(season.name())) return season;
            }
            return null;
        }
    }

}

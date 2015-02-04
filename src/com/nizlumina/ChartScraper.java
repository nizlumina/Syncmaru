package com.nizlumina;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ChartScraper
{
    //We only run this once a month. Promise.
    private static final String endpoint = "https://livechart.me/";
    private static final String objectSelector = ".card.ng-scope";
    private static final String innerMal = "thumb-link";
    private static final String innerTitle = "card__title";
    private static final String innerStudio = "card__studio";
    private static final String innerSource = "info_box", innerSourceString = "Source";
    private boolean logging = false;

    public void setLogging(boolean enabled)
    {
        logging = enabled;
    }

    public void scrapeData(File htmlFile)
    {
        try
        {
            process(Jsoup.parse(htmlFile, "UTF-8"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void scrapeDataOnline()
    {
        final WebUnit unit = new WebUnit();

        try
        {
            unit.enqueueGetString(endpoint, new WebUnit.WebUnitStringListener()
            {
                @Override
                public void onFailure()
                {

                }

                @Override
                public void onFinish(String responseBody)
                {

                    if (responseBody != null)
                    {
                        process(Jsoup.parse(responseBody));
                    }
                }
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void process(Document document)
    {
        Season currSeason = Season.getSeason(document.title());
        System.out.print("Current season: " + currSeason.name());

        //Get each main card
        Elements cardElements = document.select(objectSelector);
        System.out.println("\nElement Size: " + cardElements.size());

        int index = 0;
        for (Element card : cardElements)
        {
            ScrapeObject object = new ScrapeObject();

            //Get MAL
            Elements malClassRaws = card.getElementsByClass(innerMal);
            if (malClassRaws.size() > 0)
            {
                //first index of the element contains a link to MAL
                String malLink = malClassRaws.get(0).attr("href");
                if (logging)
                    System.out.println("\nCard index: " + index++);
                System.out.println(malLink);

                object.setLink(malLink);
                object.setId(parseMALId(malLink));
            }

            //Get title (though title in the database will still follow Hummingbird/MAL)
            Elements cardTitleRaws = card.getElementsByClass(innerTitle);
            for (Element cardTitleRaw : cardTitleRaws)
            {
                object.setName(cardTitleRaw.child(0).ownText());
            }

            //Get studio
            Elements cardStudioRaws = card.getElementsByClass(innerStudio);
            for (Element cardStudio : cardStudioRaws)
            {
                object.setStudio(cardStudio.child(0).ownText());
            }

            //Get source type
            Elements infoBoxesRaws = card.getElementsByClass(innerSource);
            for (Element infoBox : infoBoxesRaws)
            {
                String innerValue = infoBox.ownText();
                if (innerValue.equalsIgnoreCase(innerSourceString))
                {
                    object.setSource(infoBox.child(0).ownText());
                    break;
                }
            }

            if (logging) System.out.println("\n" + object.getLoggingData());
        }
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

    public static class ScrapeObject
    {
        String id;
        String name;
        String link;
        String studio;
        String source;

        public String getSource()
        {
            return source;
        }

        public void setSource(String source)
        {
            this.source = source;
        }

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getLink()
        {
            return link;
        }

        public void setLink(String link)
        {
            this.link = link;
        }

        public String getStudio()
        {
            return studio;
        }

        public void setStudio(String studio)
        {
            this.studio = studio;
        }

        public String getLoggingData()
        {
            return String.format("LogData:\nID: %s\nName: %s\nSource: %s\nStudio: %s\nLink: %s\n",
                    getId(), getName(), getSource(), getStudio(), getLink());
        }
    }
}

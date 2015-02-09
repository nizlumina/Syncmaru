package com.nizlumina.scraper;

import com.nizlumina.model.LiveChartObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class LiveChartScraper
{

    private static final String OBJECT_IDENTIFIER = "article";

    public static List<LiveChartObject> scrape(String html)
    {
        List<LiveChartObject> output = new ArrayList<LiveChartObject>();

        Document document = Jsoup.parse(html);

        Elements seriesObjects = document.getElementsByTag(OBJECT_IDENTIFIER);
        for (Element seriesObject : seriesObjects)
        {
            LiveChartObject chartObject = processObject(seriesObject);
            if (chartObject != null) output.add(chartObject);
        }
        return output;
    }

    private static void log(LiveChartObject chartObject)
    {
        System.out.println(chartObject.getTitle());
    }

    private static LiveChartObject processObject(Element seriesObject)
    {
        LiveChartObject chartObject = new LiveChartObject();

        processCommonValue(chartObject, seriesObject);
        processOptionals(chartObject, seriesObject);

        return chartObject;
    }


    private static void processCommonValue(LiveChartObject chartObject, Element seriesObject)
    {
        chartObject.setCategory(seriesObject.attr("data-category"));
        chartObject.setTitle(seriesObject.getElementsByClass("title").get(0).ownText().trim());
        chartObject.setStudio(seriesObject.getElementsByClass("studio").get(0).text());
    }


    //process anything that may not be set
    private static void processOptionals(LiveChartObject chartObject, Element seriesObject)
    {
        parseLinks(chartObject, seriesObject);
    }

    private static void parseInfoHalf(LiveChartObject chartObject, Element seriesObject)
    {
        Elements infos = seriesObject.getElementsByClass("half");
        for (Element info : infos)
        {
            String identifier = info.attr("data-tooltip");
            if (identifier != null && identifier.length() > 0)
            {
                if (identifier.equalsIgnoreCase("type"))
                {
                    chartObject.setType(info.text());
                }
                else if (identifier.equalsIgnoreCase("source"))
                {
                    chartObject.setSource(info.text());
                }
            }
        }
    }

    private static void parseLinks(LiveChartObject chartObject, Element seriesObject)
    {
        Element element = seriesObject.getElementsByClass("links").get(0);
        Elements links = element.select("a[href]");
        for (Element linkElement : links)
        {
            String className = linkElement.className();
            String link = linkElement.attr("abs:href");

            if (link != null)
            {
                if (className.equalsIgnoreCase("website"))
                {
                    chartObject.setWebsite(link);
                }
                else if (className.equalsIgnoreCase("mal"))
                {
                    chartObject.setMalLink("mal");
                    chartObject.setMalID(malParseID(link));
                }
                else if (className.equalsIgnoreCase("hummingbird"))
                {
                    chartObject.setHummingbirdLink(link);
                    chartObject.setHummingbirdSlug(hummingbirdParseSlug(link));
                }
                else if (className.equalsIgnoreCase("crunchyroll"))
                {
                    chartObject.setCrunchyrollLink(link);
                }
            }
        }
    }


    //Feel free to copypasta

    public static String malParseID(String malLink)
    {
        int startIndex = malLink.indexOf("anime/") + 6;
        String idTailed = malLink.substring(startIndex);
        int endIndex = idTailed.indexOf("/");
        if (endIndex > 0)
            return idTailed.substring(0, endIndex);
        else return idTailed.trim();
    }

    public static String hummingbirdParseSlug(String hummingbirdLink)
    {
        return malParseID(hummingbirdLink); //uh. this works. Just putting this here in case of future changes.
    }
}

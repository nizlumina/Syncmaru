package com.nizlumina;

import com.nizlumina.model.LiveChartObject;
import com.nizlumina.scraper.ChartScraper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Syncmaru
{
    public static final String FIREBASE_SECRETKEY = Config.FIREBASE_SECRET_KEY; //insert your own secret key here

    public static final String MAL_USERAGENT = Config.MAL_USERAGENT; //insert a whitelisted MAL API User agent;
    public static final String MAL_USERNAME = Config.MAL_USERNAME; //needed to bypass MAL challenge
    public static final String MAL_PASSWORD = Config.MAL_PASSWORD; //needed to bypass MAL challenge

    public static final String HUMMINGBIRD_CLIENTID = Config.HUMMINGBIRD_CLIENTID; //insert your registered Hummingbird key here
    public static final String HUMMINGBIRD_USERAGENT = Config.HUMMINGBIRD_USERAGENT;

    //Full chart can be obtained via Chrome element inspection
    private static final String filePath = "raw/sample_livechart.html";

    public static void main(String[] args) throws IOException
    {
        Scanner scanner = new Scanner(System.in);
        ChartScraper scraper = new ChartScraper();
        scraper.setLogging(false);
        File htmlFile = new File(filePath);
        if (htmlFile.exists())
        {
            List<LiveChartObject> results = scraper.scrapeData(htmlFile);

            for (LiveChartObject result : results)
            {
                System.out.println(result.getLoggingData());
            }

            System.out.println("File have been parsed with result size: " + results.size());
            System.out.println("\nPress Enter to proceed with Hummingbird matching. This will use any available network and will take sometime to finish");
            scanner.nextLine();
            System.out.println("Proceeded. Please have a coffee while waiting.");

            //match with hummingbird
            //resolve to final data
            //push to Firebase
            SyncmaruProcessor processor = new SyncmaruProcessor(results, scraper.getScrapedSeason(), 2014);
            processor.processLinkage();

        }
        else System.out.print("Chart HTML file not found");
    }
}

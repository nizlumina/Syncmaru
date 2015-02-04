package com.nizlumina;

import com.nizlumina.factory.MALJSONFactory;
import com.nizlumina.model.LiveChartObject;
import com.nizlumina.model.MALObject;
import com.nizlumina.scraper.ChartScraper;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Syncmaru
{
    public static final String FIREBASE_SECRETKEY = Config.FIREBASE_SECRET_KEY; //insert your own secret key here
    public static final String MAL_USERAGENT = Config.MAL_USERAGENT; //insert a whitelisted MAL API User agent (if not available) comment out the MAL method in main()

    //Full chart can be obtained via Chrome element inspection
    private static final String filePath = "raw/sample_livechart.html";

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        ChartScraper scraper = new ChartScraper();
        scraper.setLogging(false);
        File htmlFile = new File(filePath);
        if (htmlFile.exists())
        {
            List<LiveChartObject> results = scraper.scrapeData(htmlFile);
            System.out.println("File have been parsed with result size: " + results.size());
            for (LiveChartObject result : results)
            {
                System.out.println(result.getLoggingData());
            }
            System.out.println("\nPress Enter to proceed with Hummingbird matching. This will use any available network and will take sometime to finish");
            scanner.nextLine();
            System.out.println("Proceeded. Please have a coffee while waiting.");

            List<MALObject> malObjects = MALJSONFactory.fromXML(null);
            for (MALObject malObject : malObjects)
            {

                System.out.println(malObject.getId() + " " + malObject.getSynonyms());
            }
            //match with hummingbird
            //resolve to final data
            //push to Firebase

        }
        else System.out.print("File not found");
    }
}

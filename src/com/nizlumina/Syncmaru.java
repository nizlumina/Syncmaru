package com.nizlumina;

import com.nizlumina.factory.LiveChartWebFactory;
import com.nizlumina.model.FirebasePush;
import com.nizlumina.model.LiveChartObject;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Syncmaru
{
    public static final String FIREBASE_DEFAULT_ENDPOINT = Config.FIREBASE_DEFAULT_ENDPOINT; //insert your own firebase endpoint here (without the http://)
    public static final String FIREBASE_SECRETKEY = Config.FIREBASE_SECRET_KEY; //insert your own secret key here

    public static final String MAL_USERAGENT = Config.MAL_USERAGENT; //insert a whitelisted MAL API User agent;
    public static final String MAL_USERNAME = Config.MAL_USERNAME; //needed to bypass MAL challenge
    public static final String MAL_PASSWORD = Config.MAL_PASSWORD; //needed to bypass MAL challenge

    public static final String HUMMINGBIRD_CLIENTID = Config.HUMMINGBIRD_CLIENTID; //insert your registered Hummingbird key here
    public static final String HUMMINGBIRD_USERAGENT = Config.HUMMINGBIRD_USERAGENT;

    //Full chart can be obtained via Chrome element inspection

    public static void main(String[] args) throws IOException
    {
        boolean quit = false;
        while (!quit)
        {
            Scanner scanner = new Scanner(System.in);
            log("Insert input:\nP: Parse LiveChart chart (web)\nU: Start Firebase jobs");
            String firstInput = scanner.nextLine();

            if (firstInput.equalsIgnoreCase("P"))
            {
                log("Select season:");
                int i = 0;
                for (LiveChartWebFactory.Season season : LiveChartWebFactory.Season.values())
                {
                    log(i++ + " - " + season.name());
                }
                int seasonChosen = Integer.parseInt(scanner.nextLine());

                String seasonChosenString = null;

                for (LiveChartWebFactory.Season season : LiveChartWebFactory.Season.values())
                {
                    if (season.ordinal() == seasonChosen) seasonChosenString = season.toString();
                }

                if (seasonChosenString != null)
                {
                    log("Chosen: " + seasonChosenString);

                    log("\nInput year (minimum 2013):");
                    String yearChosen = scanner.nextLine();

                    log("Chosen: " + yearChosen);


                    List<LiveChartObject> chartObjects = LiveChartWebFactory.requestChart(LiveChartWebFactory.Season.valueOf(seasonChosenString), yearChosen);

                    log("Results size:" + chartObjects.size());
                    //match with hummingbird
                    //resolve to final data
                    //save to local path
                    //SyncmaruProcessor processor = new SyncmaruProcessor(results, scraper.getScrapedSeason(), 2014);
                    SyncmaruProcessor processor = new SyncmaruProcessor(chartObjects, seasonChosenString, yearChosen);
                    processor.processLinkage();

                }
                else log("Chosen season is wrong!");
            }
            if (firstInput.equalsIgnoreCase("U"))
            {
                log("\nStarting firebase jobs\n");

                log("Default Endpoint: " + FIREBASE_DEFAULT_ENDPOINT);
                log("INPUT AN ENDPOINT: eg: yourapp.firebaseio.com \nEntering nothing will use the FIREBASE_DEFAULT_ENDPOINT in Syncmaru.java \n(https:// is automatically appended at the beginning. Extra path can be set after this)\n");

                String kbInput = scanner.nextLine().trim();
                String endpoint;
                if (kbInput.length() > 0) endpoint = kbInput;
                else endpoint = FIREBASE_DEFAULT_ENDPOINT;
                boolean exit = false;
                while (!exit)
                {
                    processFirebase(scanner, endpoint);

                    log("Exit? Input Y to exit current task, Q to fully quit, or anything else to return back for payload jobs");
                    String exitInput = scanner.nextLine();
                    if (exitInput.equalsIgnoreCase("Y"))
                    {
                        log("Payload task exited");
                        exit = true;
                    }
                    if (exitInput.equalsIgnoreCase("Q"))
                    {
                        exit = true;
                        quit = true;
                    }
                }

            }
        }


    }

    private static void processFirebase(Scanner scanner, String endpoint)
    {
        String formedEndpoint = "https://" + endpoint;
        log("Choose payloads");
        File[] payloads = new File("jsonpayloads").listFiles();
        if (payloads != null)
        {
            int i = 0;
            for (File file : payloads)
            {
                log("[" + i++ + "]" + file.getName());
            }
        }

        try
        {
            int intInput = scanner.nextInt();

            log(payloads[intInput].getName() + " chosen\n");
            final String selectedJSONPayload = IOUtils.toString(new FileInputStream(payloads[intInput]));

            log("Using FIREBASE_SECRETKEY as declared in Syncmaru.");

            boolean upload = false;
            while (!upload)
            {
                FirebasePush.Builder builder = new FirebasePush.Builder(FIREBASE_SECRETKEY, formedEndpoint);

                log("Insert new path e.g /customer/americans");

                String pathInput = scanner.nextLine();
                String sanitized = pathInput.trim();
                if (sanitized.length() > 0)
                {
                    builder.appendPath(sanitized);
                }

                log("PATH:\n" + builder.getSamplePath());

                log("\nInput Y to proceed with uploading or ENTER to repair current path");

                String proceedUploadString = scanner.nextLine();
                if (proceedUploadString.trim().equalsIgnoreCase("Y"))
                {
                    upload = true;
                    log("Input the HTTP method for this task (GET/PUT/DELETE etc.):\n");
                    String httpMethodInput = scanner.nextLine().toUpperCase().trim();
                    System.out.println("Pushing to endpoint. Please hold tight.");
                    boolean success = builder.build().push(httpMethodInput, selectedJSONPayload);
                    log("\n\nSuccess? " + success);
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void log(String string)
    {
        System.out.println(string);
    }
}

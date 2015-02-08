package com.nizlumina;

import com.nizlumina.model.AniChartObject;
import com.nizlumina.model.FirebasePush;
import com.nizlumina.scraper.AniChartScraper;

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
            log("Insert input:\nP: Parse charts\nU: Skip parsing, and start Firebase jobs");
            String firstInput = scanner.nextLine();

            if (firstInput.equalsIgnoreCase("P"))
            {
                log("Select input file:");
                File[] inputs = new File("input").listFiles();
                if (inputs != null)
                {
                    int i = 0;
                    for (File file : inputs)
                    {
                        log("[" + i++ + "]" + file.getName());
                    }
                }

                int intInput = scanner.nextInt();
                log(intInput + " chosen");


                assert inputs != null;
                File htmlFile = inputs[intInput];

                if (htmlFile.exists())
                {
                    AniChartScraper scraper = new AniChartScraper();
                    scraper.setLogging(false);
                    List<AniChartObject> results = scraper.scrapeData(htmlFile);

                    for (AniChartObject result : results)
                    {
                        log(result.getLoggingData());
                    }

                    log("File have been parsed with result size: " + results.size());
                    log("\nPress Enter to proceed with Hummingbird matching. This will use any available network and will take sometime to finish");
                    scanner.nextLine();
                    scanner.close();
                    log("Proceeded. Please have a coffee while waiting.");

                    //match with hummingbird
                    //resolve to final data
                    //save to local path
                    SyncmaruProcessor processor = new SyncmaruProcessor(results, scraper.getScrapedSeason(), 2014);
                    processor.processLinkage();

                }
                else log("Chart HTML file not found");
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

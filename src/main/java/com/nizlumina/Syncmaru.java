package com.nizlumina;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nizlumina.factory.LiveChartWebFactory;
import com.nizlumina.model.FirebaseUnit;
import com.nizlumina.model.LiveChartObject;
import com.nizlumina.model.Season;
import com.nizlumina.utils.SeasonType;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private static final String HASH_DEFAULT_PATH = "/seasons";

    //Full chart can be obtained via Chrome element inspection

    public static void main(String[] args) throws IOException
    {
        boolean quit = false;
        while (!quit)
        {
            Scanner scanner = new Scanner(System.in);
            log("Insert input:\nP: Parse LiveChart chart (web)\nU: Start Firebase upload jobs\nH: Start Firebase JSON hashing jobs\nA: For Auto (Quickly do all above)");
            String firstInput = scanner.nextLine();

            if (firstInput.equalsIgnoreCase("P"))
            {
                processParse(scanner);
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
                processFirebaseUpload(scanner, endpoint);


            }

            if (firstInput.equalsIgnoreCase("H"))
            {
                processHashingUpload(scanner);
            }

            if (firstInput.equalsIgnoreCase("A"))
            {
                processAuto(scanner);
            }


            log("Exit? Q to fully quit, or anything else to return back for payload jobs");
            String exitInput = scanner.nextLine();
            if (exitInput.equalsIgnoreCase("Q"))
            {
                quit = true;
            }
        }


    }

    private static void processParse(Scanner scanner)
    {
        String seasonChosenString = chooseSeason(scanner);

        if (seasonChosenString != null)
        {
            log("Chosen: " + seasonChosenString);

            String yearChosen = chooseYear(scanner);


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

    private static String chooseSeason(Scanner scanner)
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
        return seasonChosenString;
    }

    //Since I don't want to bother with hosting and Firebase Cache-Control header is only supported thru hosted app and not the default database accessed via REST API)
    private static void processHashingUpload(Scanner scanner)
    {
        log("Using default endpoint.");

        String pathInput = null;
        boolean pathDecided = false;
        while (!pathDecided)
        {
            log("Input the relative path to hash its JSON e.g /anime/winter/2015");
            pathInput = scanner.nextLine();
            log("Path to be hashed [" + pathInput + "]");
            log("ENTER to continue or F to fix above path");
            if (!scanner.nextLine().equalsIgnoreCase("F")) pathDecided = true;
        }
        updateHashIndex(pathInput, false);
    }

    private static boolean updateHashIndex(String pathInput, boolean fromAuto)
    {
        boolean uploadTask = false;
        if (pathInput != null)
        {
            log("Automated task started");
            log("Using default URL key for hash index:" + HASH_DEFAULT_PATH);
            Gson gson = new Gson();
            String formedEndpoint = FIREBASE_DEFAULT_ENDPOINT + HASH_DEFAULT_PATH;
            if (!formedEndpoint.startsWith("https://"))
            {
                formedEndpoint = "https://" + formedEndpoint;
            }

            //Get the index

            FirebaseUnit hashIndexGet = new FirebaseUnit.Builder(FIREBASE_SECRETKEY, formedEndpoint).build();
            String indexJSON = hashIndexGet.getString();
            TypeToken<List<Season>> hashTypeToken = new TypeToken<List<Season>>() {};
            List<Season> seasonList = gson.fromJson(indexJSON, hashTypeToken.getType());
            if (seasonList == null) seasonList = new ArrayList<Season>();

            //Hash the JSON response in the url
            String hashingURL;
            if (!fromAuto)
                hashingURL = FIREBASE_DEFAULT_ENDPOINT + pathInput;
            else
                hashingURL = pathInput;

            if (!hashingURL.startsWith("https://"))
            {
                hashingURL = "https://" + hashingURL;
            }

            log(hashingURL);
            FirebaseUnit firebaseGet = new FirebaseUnit.Builder(FIREBASE_SECRETKEY, hashingURL).build();

            String json = firebaseGet.getString();
            String hashOutput = DigestUtils.md5Hex(json);
            log("HASH " + hashOutput);

            //Roll your own if you want
            int subIndex = pathInput.indexOf("/anime/");
            String seasonInput = pathInput.substring(subIndex, pathInput.length()).replace("/anime/", "").replaceAll("\\W+", "-");
            String stringArr[] = seasonInput.split("-");
            Season season = new Season(stringArr[0], Integer.parseInt(stringArr[1].trim()), hashOutput);
            log(season.getSeason() + " [" + season.getYear() + "]");

            //Check index
            Map<String, Season> searchMap = new HashMap<String, Season>();
            for (Season indexedSeason : seasonList)
            {
                searchMap.put(indexedSeason.getIndexKey(), indexedSeason);
            }
            searchMap.put(season.getIndexKey(), season); //update

            List<Season> finalIndex = new ArrayList<Season>(searchMap.values());

            //sort
            Collections.sort(finalIndex, new Comparator<Season>()
            {
                @Override
                public int compare(Season o1, Season o2)
                {
                    SeasonType seasonA = SeasonType.fromString(o1.getSeason());
                    SeasonType seasonB = SeasonType.fromString(o2.getSeason());
                    int firstCompare = o1.getYear() - o2.getYear();
                    if (firstCompare == 0)
                        return seasonA.compareTo(seasonB);
                    return firstCompare;
                }
            });

            String payload = gson.toJson(finalIndex, hashTypeToken.getType()); //important to put them as list
            FirebaseUnit firebasePush = new FirebaseUnit.Builder(FIREBASE_SECRETKEY, formedEndpoint).build();
            uploadTask = firebasePush.push("PUT", payload);
        }
        log("Upload task success: " + String.valueOf(uploadTask).toUpperCase());
        return uploadTask;
    }

    private static boolean autoProcessFirebaseUpload(File file, String endpoint, String method)
    {
        try
        {
            final String selectedJSONPayload = IOUtils.toString(new FileInputStream(file));
            FirebaseUnit.Builder builder = new FirebaseUnit.Builder(FIREBASE_SECRETKEY, endpoint);
            return builder.build().push(method, selectedJSONPayload);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private static void processFirebaseUpload(Scanner scanner, String endpoint)
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
                FirebaseUnit.Builder builder = new FirebaseUnit.Builder(FIREBASE_SECRETKEY, formedEndpoint);

                log("Insert new path e.g /customer/americans");

                String pathInput = scanner.nextLine();
                String sanitized = pathInput.trim();
                if (sanitized.length() > 0)
                {
                    builder.appendPath(sanitized);
                }

                log("PATH:\n" + builder.getSamplePath());

                log("\nInput Y to proceed with uploading or anything else to repair current path");

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

    private static void processAuto(Scanner scanner)
    {

        String seasonChosenString = chooseSeason(scanner);
        log("Chosen: " + seasonChosenString);

        if (seasonChosenString != null)
        {
            String yearChosen = chooseYear(scanner);

            List<LiveChartObject> chartObjects = LiveChartWebFactory.requestChart(LiveChartWebFactory.Season.valueOf(seasonChosenString), yearChosen);

            log("Results size:" + chartObjects.size());

            SyncmaruProcessor processor = new SyncmaruProcessor(chartObjects, seasonChosenString, yearChosen);
            File json = processor.processLinkage();
            String endpoint = "https://" + FIREBASE_DEFAULT_ENDPOINT + "/anime/" + seasonChosenString.toLowerCase() + "/" + yearChosen;
            log("ENDPOINT: " + endpoint);
            boolean successUpload = autoProcessFirebaseUpload(json, endpoint, "PUT");

            if (successUpload)
            {
                updateHashIndex(endpoint, true);
            }
        }
        else log("Chosen season is wrong!");

    }

    private static String chooseYear(Scanner scanner)
    {
        log("\nInput year (minimum 2013):");
        String yearChosen = scanner.nextLine();

        log("Chosen: " + yearChosen);
        return yearChosen;
    }

    private static void log(String string)
    {
        System.out.println(string);
    }
}

package com.nizlumina;

import java.io.File;

public class Main
{
    //Full chart can be obtained via Chrome element inspection
    private static final String filePath = "raw/sample_livechart.html";

    public static void main(String[] args)
    {
        ChartScraper scraper = new ChartScraper();
        scraper.setLogging(true);
        File htmlFile = new File(filePath);
        if (htmlFile.exists()) scraper.scrapeData(htmlFile);
        else System.out.print("File not found");
    }
}

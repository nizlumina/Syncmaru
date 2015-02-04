package com.nizlumina.factory;


import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.Arrays;

public class MALQueryFactory
{
    public static final String MAL_SEARCH_ENDPOINT = "http://myanimelist.net/api/anime/search.xml";

    public static String getSparseTerms(String searchTerms, int termsLimitCount)
    {
        StringBuilder builder = new StringBuilder();
        String[] terms = searchTerms.split(" ");
        String[] gutted = Arrays.copyOf(terms, termsLimitCount);
        for (String gut : gutted)
        {
            if (gut != null) builder.append(gut.trim());
        }
        return builder.toString();
    }

    public static String buildQuery(String searchTerms, int termsLimitCount)
    {
        try
        {
            String finalQuery = getSparseTerms(searchTerms, termsLimitCount);
            URIBuilder builder = new URIBuilder(MAL_SEARCH_ENDPOINT).addParameter("q", finalQuery);
            return builder.build().toString();
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

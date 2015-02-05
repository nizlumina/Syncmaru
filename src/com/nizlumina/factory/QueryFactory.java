package com.nizlumina.factory;


import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QueryFactory
{
    public static final String MAL_SEARCH_ENDPOINT = "http://myanimelist.net/api/anime/search.xml";
    public static final String HUMMINGBIRD_APIV2_ENDPOINT = "https://hummingbird.me/api/v2/anime";


    public static String buildMALSearchQuery(String searchTerms, int termsLimitCount)
    {
        try
        {
            String finalQuery = getSparseTerms(searchTerms.toLowerCase(), termsLimitCount);

            URIBuilder builder = new URIBuilder(MAL_SEARCH_ENDPOINT).addParameter("q", finalQuery);
            return builder.build().toString();
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSparseTerms(String searchTerms, int termsLimitCount)
    {
        String[] terms = searchTerms.split("[ _!;:+#.-]+|[☆★]+"); //partition all the stupid sentence breaker in titles
        //System.out.println(terms.length);
        //Trim
        for (int i = 0; i < terms.length; i++)
        {
            String trimmed = terms[i].trim();
            terms[i] = trimmed;
        }

        //try longest
        List<String> sanitizedLongest = new ArrayList<String>();
        for (String term : terms)
        {
            String sanitized = sanitizeCommonChars(term).trim(); //remove the rest of the poo
            if (isSanitizedForMAL(sanitized))
            {
                sanitizedLongest.add(sanitized);
            }
        }

        Collections.sort(sanitizedLongest, new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                return o2.length() - o1.length(); //longest at the top
            }
        });

        if (termsLimitCount == 1 && sanitizedLongest.size() > 0)
            return sanitizedLongest.get(0);
        else
        {
            //try other strategy if not using longest
            StringBuilder builder = new StringBuilder();
            for (String originalTerm : terms)
            {
                if (isSanitizedForMAL(originalTerm) && originalTerm.length() > 2 && termsLimitCount-- > 0)
                {
                    builder.append(originalTerm);
                    builder.append(' ');
                }
            }
            return builder.toString().trim();
        }
    }

    public static String sanitizeCommonChars(String input)
    {
        return input.replaceAll("\\W", " ").replaceAll("@", "a");
    }

    public static boolean isSanitizedForMAL(String input)
    {
        return input.matches("\\w+");
    }

    public static String buildHummingbirdGetQuery(String malID)
    {
        //URIBuilder builder = new URIBuilder(HUMMINGBIRD_APIV2_ENDPOINT).addParameter("myanimelist:", malID);
        //return builder.build().toString();
        final String relativePath = "/myanimelist:";
        return HUMMINGBIRD_APIV2_ENDPOINT + relativePath + malID;
    }
}

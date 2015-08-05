package com.nizlumina.factory;


import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MALQueryFactory
{
    public static final String MAL_SEARCH_ENDPOINT = "http://myanimelist.net/api/anime/search.xml";

    public static String buildMALSearchQuery(String searchTerms, Strategy strategy, int termCounts)
    {
        String finalQuery = getTerms(searchTerms.toLowerCase(), strategy, termCounts);
        return buildString(finalQuery);
    }

    private static String buildString(String finalQuery)
    {
        try
        {
            URIBuilder builder = new URIBuilder(MAL_SEARCH_ENDPOINT).addParameter("q", finalQuery);
            return builder.build().toString();
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static String getTerms(String searchTerms, Strategy strategy, int termsLimitCount)
    {
        String[] terms = searchTerms.split("[ _!;:+#.-]+|[☆★]+"); //partition all the stupid sentence breaker in titles
        for (int i = 0; i < terms.length; i++)
        {
            String trimmed = terms[i].trim();
            terms[i] = trimmed;
        }

        //first strategy with a big capture net
        if (strategy == Strategy.BY_TERMCOUNTS)
        {
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

        //tactical. apply this first since they have a good success rate
        if (strategy == Strategy.LONGEST || strategy == Strategy.SECOND_LONGEST)
        {
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
            if (sanitizedLongest.size() > 0)
            {
                if (strategy == Strategy.LONGEST)
                {
                    return sanitizedLongest.get(0);
                }
                //noinspection ConstantConditions
                if (strategy == Strategy.SECOND_LONGEST && sanitizedLongest.size() > 1)
                {
                    return sanitizedLongest.get(1);
                }
            }

        }
        return null;
    }

    public static String sanitizeCommonChars(String input)
    {
        return input.replaceAll("\\W", " ").replaceAll("@", "a");
    }

    public static boolean isSanitizedForMAL(String input)
    {
        return input.matches("\\w+");
    }

    public enum Strategy
    {
        BY_TERMCOUNTS, LONGEST, SECOND_LONGEST
    }

}

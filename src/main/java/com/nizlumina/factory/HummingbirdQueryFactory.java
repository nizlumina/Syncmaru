package com.nizlumina.factory;

/**
 * Created by archi_000 on 2/9/2015.
 */
public class HummingbirdQueryFactory
{
    public static final String HUMMINGBIRD_APIV2_ENDPOINT = "https://hummingbird.me/api/v2/anime";

    public static String buildHummingbirdGetQuery(String malID)
    {
        //URIBuilder builder = new URIBuilder(HUMMINGBIRD_APIV2_ENDPOINT).addParameter("myanimelist:", malID);
        //return builder.build().toString();
        final String relativePath = "/myanimelist:";
        return HUMMINGBIRD_APIV2_ENDPOINT + relativePath + malID;
    }
}

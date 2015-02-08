package com.nizlumina.factory;

import com.nizlumina.model.AniChartObject;
import com.sun.javafx.beans.annotations.NonNull;

import java.util.List;

/**
 * Static factory
 */
public class MatchingFactory
{
    public static void matchToHummingbird(@NonNull List<AniChartObject> chartObjects)
    {
        for (AniChartObject chartObject : chartObjects)
        {
            String malID = chartObject.getId();
        }
    }
}

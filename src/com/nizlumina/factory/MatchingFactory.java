package com.nizlumina.factory;

import com.nizlumina.model.LiveChartObject;
import com.sun.javafx.beans.annotations.NonNull;

import java.util.List;

/**
 * Static factory
 */
public class MatchingFactory
{
    public static void matchToHummingbird(@NonNull List<LiveChartObject> chartObjects)
    {
        for (LiveChartObject chartObject : chartObjects)
        {
            String malID = chartObject.getId();
        }
    }
}

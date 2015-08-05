package com.nizlumina.utils;

import java.util.Calendar;

public enum SeasonType
{
    WINTER, SPRING, SUMMER, FALL;

    public static SeasonType fromString(String input)
    {
        for (SeasonType seasonType : SeasonType.values())
        {
            if (seasonType.name().equalsIgnoreCase(input)) return seasonType;
        }
        return null;
    }

    public static SeasonType getCurrentSeason()
    {
        int month = Calendar.getInstance().get(Calendar.MONTH);

        if (month < Calendar.APRIL) return WINTER;
        else if (month < Calendar.JULY) return SPRING;
        else if (month < Calendar.OCTOBER) return SUMMER;
        else return FALL;
    }

}

package com.tknilsson.habitboss.model;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.HashMap;


public class Habits extends HashMap<Habit.TimeWindow, ArrayList<Habit>> {

    public static String toJson(Habits habits){
        return "json";
    }

    public static Habits fromJson(String json){
        return new Habits();
    }

}

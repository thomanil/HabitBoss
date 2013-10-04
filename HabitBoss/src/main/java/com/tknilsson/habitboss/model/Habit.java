package com.tknilsson.habitboss.model;

import org.joda.time.DateTime;


public class Habit {

    public enum TimeWindow {
        DAILY, WEEKLY, MONTHLY
    }

    public enum Kind {
        GOOD, BAD
    }

    private Habit (){
        // Force us to always instantiate with kind and timewindow
    }

    public Habit(Kind kind, TimeWindow timeWindow){
        this.kind = kind;
        this.timeWindow = timeWindow;
        startNewStreakNow();
    }

    private TimeWindow timeWindow;
    private Kind kind;
    private DateTime lastTicked;
    private DateTime startOfCurrentStreak;

    public void startNewStreakNow(){
        startOfCurrentStreak = new DateTime();
    }

    public boolean isSoonDue() {
        // TRUE if less than an hour/day from next day/week/month
        return false;
    }

    public boolean isOverdue() {
        // TRUE if more than one day/week/month since lastConfirmed
        return false;
    }

}

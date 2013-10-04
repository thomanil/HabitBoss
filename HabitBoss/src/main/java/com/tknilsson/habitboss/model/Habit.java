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

    public Habit(Kind kind, TimeWindow timeWindow, String description){
        this.kind = kind;
        this.timeWindow = timeWindow;
        this.setDescription(description);
        startNewStreakNow();
    }

    // public Long id // TODO for greenDAO?
    private TimeWindow timeWindow;
    private Kind kind;
    private String description;
    private DateTime lastTicked;
    private DateTime startOfCurrentStreak;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void startNewStreakNow(){
        startOfCurrentStreak = new DateTime();
    }

    // TODO "tick" method
    // TODO "fail" method (end streak)

    public boolean isSoonDue() {
        // TODO test/impl: TRUE if less than an hour/day from next day/week/month
        return false;
    }

    public boolean isOverdue() {
        // TODO test/iompl: TRUE if more than one day/week/month since lastConfirmed
        return false;
    }



}

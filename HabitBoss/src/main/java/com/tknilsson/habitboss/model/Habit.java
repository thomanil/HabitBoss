package com.tknilsson.habitboss.model;

import org.joda.time.DateTime;


public class Habit {

    public enum TimeWindow {
        DAILY, WEEKLY, MONTHLY
    }

    public enum Kind {
        GOOD, BAD
    }

    private TimeWindow timeWindow;
    private Kind kind;
    private String description;
    private DateTime lastTicked;
    private DateTime startOfCurrentStreak;

    private Habit (){
        // Force us to always instantiate with kind and timewindow
    }

    public Habit(Kind kind, TimeWindow timeWindow, String description){
        this.kind = kind;
        this.timeWindow = timeWindow;
        this.setDescription(description);
        setLastTicked(DateTime.now());
        startNewStreakNow();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected DateTime getLastTicked() {
        return lastTicked;
    }

    protected void setLastTicked(DateTime lastTicked) {
        this.lastTicked = lastTicked;
    }

    protected DateTime getStartOfCurrentStreak() {
        return startOfCurrentStreak;
    }

    protected void setStartOfCurrentStreak(DateTime startOfCurrentStreak) {
        this.startOfCurrentStreak = startOfCurrentStreak;
    }

    public void startNewStreakNow(){
        setStartOfCurrentStreak(new DateTime());
    }

    public void markAsDone(){
        description += " DONE!"; // TODO
    }

    public void fail(){
        description += " FAILED!"; // TODO
        startNewStreakNow();
    }

    public boolean isOverdue() {
        if(timeWindow.equals(TimeWindow.DAILY)){
           return DateTime.now().isAfter(lastTicked.plusDays(1));
        } else if(timeWindow.equals(TimeWindow.WEEKLY)){
            return DateTime.now().isAfter(lastTicked.plusWeeks(1));
        } else if(timeWindow.equals(TimeWindow.MONTHLY)){
            return DateTime.now().isAfter(lastTicked.plusMonths(1));
        } else {
            throw new RuntimeException("Unexpected timewindow type");
        }
    }

    public boolean isSoonDue() {
        // TODO test/impl: TRUE if if less than an hour/day from next day/week/month
        return false;
    }



}

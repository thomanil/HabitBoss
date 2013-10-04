package com.tknilsson.habitboss.model;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;
import org.joda.time.Interval;


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
        if(isSoonDue()){
            return " (DUE SOON) " + description;
        } else if(isOverdue()){
            return " (OVERDUE) " + description;
        } else {
            return description;
        }
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
        setLastTicked(DateTime.now());
    }

    public boolean canBeMarkedAsDoneAgain(){
        if(isOverdue() || isSoonDue()){
            return true;
        }

        if(timeWindow.equals(TimeWindow.DAILY)){
            DateTime lastMidnight = DateTime.now().withTimeAtStartOfDay();
            return (lastTicked.isBefore(lastMidnight));
        } else if(timeWindow.equals(TimeWindow.WEEKLY)){
            DateTime lastSunday = DateTime.now().withDayOfWeek(DateTimeConstants.SUNDAY).minusDays(7);
            return (lastTicked.isBefore(lastSunday));
        } else if(timeWindow.equals(TimeWindow.MONTHLY)){
            DateTime endOfLastMonth = new DateTime().minusMonths(1).dayOfMonth().withMaximumValue();
            return (lastTicked.isBefore(endOfLastMonth));
        } else {
            throw new RuntimeException("Unexpected timewindow type");
        }
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
        Duration betweenTickAndNow = (new Interval(lastTicked, DateTime.now())).toDuration();
        long minSinceTick = betweenTickAndNow.getStandardMinutes();
        long hrsSinceTick = betweenTickAndNow.getStandardHours();
        long daysSinceTick = betweenTickAndNow.getStandardDays();

        if(timeWindow.equals(TimeWindow.DAILY)){
            return (minSinceTick > (23*60)  && minSinceTick < (24*60));
        } else if(timeWindow.equals(TimeWindow.WEEKLY)){
            return (hrsSinceTick > (6*24) && hrsSinceTick < (7*24));
        } else if(timeWindow.equals(TimeWindow.MONTHLY)){
            return (daysSinceTick > 27 && daysSinceTick < 31);
        } else {
            throw new RuntimeException("Unexpected timewindow type");
        }
    }





}

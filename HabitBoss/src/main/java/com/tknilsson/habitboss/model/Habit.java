package com.tknilsson.habitboss.model;

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

    private String description;
    private String timeWindow;
    private String kind;
    private long lastTicked;
    private long startOfCurrentStreak;

    public Habit(Kind kind, TimeWindow timeWindow, String description){
        this.setKind(kind);
        this.setTimeWindow(timeWindow);
        this.setDescription(description);
        setLastTicked(DateTime.now());
        startNewStreakNow();
    }

    public String getDescription() {
        return "("+ getShorthandTimeTillDue()+") "+description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected DateTime getLastTicked() {
        return new DateTime(lastTicked);
    }

    protected void setLastTicked(DateTime lastTicked) {
        this.lastTicked = lastTicked.getMillis();
    }

    public TimeWindow getTimeWindow() {
        return TimeWindow.valueOf(timeWindow);
    }

    public void setTimeWindow(TimeWindow timeWindow) {
        this.timeWindow = timeWindow.toString();
    }

    public Kind getKind() {
        return Kind.valueOf(kind);
    }

    public void setKind(Kind kind) {
        this.kind = kind.toString();
    }

    protected DateTime getStartOfCurrentStreak() {
        return new DateTime(startOfCurrentStreak);
    }

    protected void setStartOfCurrentStreak(DateTime startOfCurrentStreak) {
        this.startOfCurrentStreak = startOfCurrentStreak.getMillis();
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

        if(getTimeWindow().equals(TimeWindow.DAILY)){
            DateTime lastMidnight = DateTime.now().withTimeAtStartOfDay();
            return (getLastTicked().isBefore(lastMidnight));
        } else if(getTimeWindow().equals(TimeWindow.WEEKLY)){
            DateTime lastSunday = DateTime.now().withDayOfWeek(DateTimeConstants.SUNDAY).minusDays(7);
            return (getLastTicked().isBefore(lastSunday));
        } else if(getTimeWindow().equals(TimeWindow.MONTHLY)){
            DateTime endOfLastMonth = new DateTime().minusMonths(1).dayOfMonth().withMaximumValue();
            return (getLastTicked().isBefore(endOfLastMonth));
        } else {
            throw new RuntimeException("Unexpected timewindow type");
        }
    }

    public void fail(){
        description += " FAILED!"; // TODO
        startNewStreakNow();
    }

    public boolean isOverdue() {
        if(getTimeWindow().equals(TimeWindow.DAILY)){
            return DateTime.now().isAfter(getLastTicked().plusDays(1));
        } else if(getTimeWindow().equals(TimeWindow.WEEKLY)){
            return DateTime.now().isAfter(getLastTicked().plusWeeks(1));
        } else if(getTimeWindow().equals(TimeWindow.MONTHLY)){
            return DateTime.now().isAfter(getLastTicked().plusMonths(1));
        } else {
            throw new RuntimeException("Unexpected timewindow type");
        }
    }

    public boolean isSoonDue() {
        Duration betweenTickAndNow = (new Interval(getLastTicked(), DateTime.now())).toDuration();
        long minSinceTick = betweenTickAndNow.getStandardMinutes();
        long hrsSinceTick = betweenTickAndNow.getStandardHours();
        long daysSinceTick = betweenTickAndNow.getStandardDays();

        if(getTimeWindow().equals(TimeWindow.DAILY)){
            return (minSinceTick > (23*60)  && minSinceTick < (24*60));
        } else if(getTimeWindow().equals(TimeWindow.WEEKLY)){
            return (hrsSinceTick > (6*24) && hrsSinceTick < (7*24));
        } else if(getTimeWindow().equals(TimeWindow.MONTHLY)){
            return (daysSinceTick > 27 && daysSinceTick < 31);
        } else {
            throw new RuntimeException("Unexpected timewindow type");
        }
    }

    private DateTime getDueDate(){
        if(getTimeWindow().equals(TimeWindow.DAILY)){
            return getLastTicked().plusHours(24);
        } else if(getTimeWindow().equals(TimeWindow.WEEKLY)){
            return getLastTicked().plusDays(7);
        } else if(getTimeWindow().equals(TimeWindow.MONTHLY)){
            return getLastTicked().plusDays(30);
        } else {
            throw new RuntimeException("Unexpected timewindow type");
        }
    }

    public String getShorthandTimeTillDue(){
        if(isOverdue()){
            return "0m";
        }

        Duration remaining = (new Interval(DateTime.now(), getDueDate())).toDuration();
        long hoursRemaining = remaining.getStandardHours();
        long minutesRemaining = remaining.getStandardMinutes();
        long daysRemaining = remaining.getStandardDays();

        // 1 hr, 2 hrs, 3 hrs [...] 23 hrs, 1day, 2days, 3days [....]
        String summary;
        if (hoursRemaining == 0){
            summary = ""+(minutesRemaining+1)+"m";
        } else if (hoursRemaining >= 1 && hoursRemaining <= 23){
            summary = ""+(hoursRemaining+1)+"h";
        } else {
            summary = ""+(daysRemaining+1)+"d";
        }

        return summary;
    }





}

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
        startNewStreakNow();

        if(getTimeWindow().equals(TimeWindow.DAILY)){
           setLastTicked(DateTime.now().minusDays(1));
        } else if(getTimeWindow().equals(TimeWindow.WEEKLY)){
            setLastTicked(DateTime.now().minusWeeks(1));
        } else if(getTimeWindow().equals(TimeWindow.MONTHLY)){
            setLastTicked(DateTime.now().minusMonths(1));
        } else {
            throw new RuntimeException("Unexpected timewindow type");
        }
    }

    public String getDescription() {
        return description;
      //TODO  //return "("+ getShorthandTimeTillDue()+") "+description;
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
        setStartOfCurrentStreak(DateTime.now());
    }

    public void markAsDone(){
        setLastTicked(DateTime.now());
    }

    public void stopWinningStreak(){
        startNewStreakNow();
    }

    public boolean isOverdue() {
        DateTime now = DateTime.now();
        DateTime lastMidnight = now.withTime(0,0,0,0).withHourOfDay(0);

        if(getTimeWindow().equals(TimeWindow.DAILY)){
            DateTime midnightBefore = lastMidnight.minusHours(24);
            return getLastTicked().isBefore(midnightBefore);
        } else if(getTimeWindow().equals(TimeWindow.WEEKLY)){
            DateTime sundayBeforeLastSunday = lastMidnight.withDayOfWeek(1).minusWeeks(1);
            return getLastTicked().isBefore(sundayBeforeLastSunday);
        } else if(getTimeWindow().equals(TimeWindow.MONTHLY)){
            DateTime monthEndBeforeLastMonthEnd = lastMidnight.withDayOfMonth(1).minusMonths(1);
            return getLastTicked().isBefore(monthEndBeforeLastMonthEnd);
        } else {
            throw new RuntimeException("Unexpected timewindow type");
        }
    }

    public boolean canBeMarkedAsDoneAgain(){
        if(isOverdue()){
            return true;
        }

        DateTime now = DateTime.now();
        DateTime lastMidnight = now.withTime(0,0,0,0).withHourOfDay(0);

        if(getTimeWindow().equals(TimeWindow.DAILY)){
            return getLastTicked().isBefore(lastMidnight);
        } else if(getTimeWindow().equals(TimeWindow.WEEKLY)){
            DateTime lastSunday = lastMidnight.withDayOfWeek(1);
            return getLastTicked().isBefore(lastSunday);
        } else if(getTimeWindow().equals(TimeWindow.MONTHLY)){
            DateTime endOfLastMonth = lastMidnight.withDayOfMonth(1);
            return getLastTicked().isBefore(endOfLastMonth);
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

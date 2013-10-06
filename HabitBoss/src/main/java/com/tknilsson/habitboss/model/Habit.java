package com.tknilsson.habitboss.model;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;
import org.joda.time.Interval;


public class Habit {

    public enum TimeWindow {
        DAILY, WEEKLY, MONTHLY
    }
    private String description;
    private String timeWindow;
    private long lastTicked;
    private long startOfCurrentStreak;

    public Habit(TimeWindow timeWindow, String description){
        this.setTimeWindow(timeWindow);
        this.setDescription(description);
        startNewStreakNow();
        setLastTickedToLastPeriod();
    }

    private void setLastTickedToLastPeriod(){
        DateTime thisHour = DateTime.now().withHourOfDay(0);
        if(getTimeWindow().equals(TimeWindow.DAILY)){
            setLastTicked(thisHour.minusDays(1));
        } else if(getTimeWindow().equals(TimeWindow.WEEKLY)){
            setLastTicked(thisHour.minusWeeks(1));
        } else if(getTimeWindow().equals(TimeWindow.MONTHLY)){
            setLastTicked(thisHour.minusMonths(1));
        } else {
            throw new RuntimeException("Unexpected timewindow type");
        }
    }

    public String getDescription() {
       return description;
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




    /*private DateTime getDueDate(){
        if(getTimeWindow().equals(TimeWindow.DAILY)){
            return getLastTicked().plusHours(24);
        } else if(getTimeWindow().equals(TimeWindow.WEEKLY)){
            return getLastTicked().plusDays(7);
        } else if(getTimeWindow().equals(TimeWindow.MONTHLY)){
            return getLastTicked().plusDays(30);
        } else {
            throw new RuntimeException("Unexpected timewindow type");
        }
    }*/

    protected DateTime getDeadline(){
        DateTime thisHour = DateTime.now().withTime(0,0,0,0);
        DateTime lastMidnight = thisHour.withHourOfDay(0);
        DateTime nextMidnight = lastMidnight.plusDays(1);
        DateTime midnightLastSunday = lastMidnight.withDayOfWeek(1);
        DateTime midnightComingSunday = lastMidnight.withDayOfWeek(1).plusDays(7);
        DateTime midnightLastMonthsEnd = lastMidnight.withDayOfMonth(1);
        DateTime midnightComingMonthsEnd = lastMidnight.withDayOfMonth(1).plusMonths(1);

        DateTime dueTime;

        if(getTimeWindow().equals(TimeWindow.DAILY)){
           if(getLastTicked().isBefore(lastMidnight)){
               dueTime = nextMidnight;
           } else {
               dueTime = nextMidnight.plusDays(1);
           }
        } else if(getTimeWindow().equals(TimeWindow.WEEKLY)){
            if(getLastTicked().isBefore(midnightLastSunday)){
                dueTime = midnightComingSunday;
            } else {
                dueTime = midnightComingSunday.plusWeeks(1);
            }
        } else if(getTimeWindow().equals(TimeWindow.MONTHLY)){
            if(getLastTicked().isBefore(midnightLastMonthsEnd)){
                dueTime = midnightComingMonthsEnd;
            } else {
                dueTime = midnightComingSunday.plusMonths(1);
            }
        } else {
            throw new RuntimeException("Unexpected timewindow type");
        }

        return dueTime;
    }

    // 0m, 1m, 3m [...] 59m, 1hr, 2h, 3h [...] 23hr, 1d, 2d, 3d [....]
    public String getTimeRemainingSummary(){
        if(isOverdue()){
            return "0m";
        }

        Duration remaining = (new Interval(DateTime.now(), getDeadline())).toDuration();
        long hoursRemaining = remaining.getStandardHours();
        long minutesRemaining = remaining.getStandardMinutes();
        long daysRemaining = remaining.getStandardDays();

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

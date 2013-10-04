package com.tknilsson.habitboss.model;


import java.util.Date;

public class Habit {

    public enum TimeType {
        DAILY, WEEKLY, MONTHLY
    }

    private TimeType timeType;

    private Date lastTicked;

    // TODO Model/persist stats on when user failed to keep/avoid the habit
    //private day/week/month fails

    public boolean isSoonDue() {
        return false;
    }

    public boolean isOverdue() {
        return false;
    }

}

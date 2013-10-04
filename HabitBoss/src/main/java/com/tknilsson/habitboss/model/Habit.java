package com.tknilsson.habitboss.model;


import java.util.Date;

public abstract class Habit {

    public enum TimeType {
        DAILY, WEEKLY, MONTHLY
    }

    private TimeType timeType;

    private Date lastTicked;

    // TODO Model/persist stats on when user failed to keep/avoid the habit
    //private day/week/month fails

}

package com.tknilsson.habitboss.model;


import java.util.Date;

public class Habit {

    public enum Temporality {
        DAILY, WEEKLY, MONTHLY
    }

    public enum Kind {
        GOOD, BAD
    }

    private Habit (){

    }

    public Habit(Kind kind, Temporality temporality){
        this.kind = kind;
        this.temporality = temporality;
    }

    private Temporality temporality;
    private Kind kind;
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

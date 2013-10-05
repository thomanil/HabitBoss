package com.tknilsson.habitboss.model;

import com.google.common.base.Predicate;
import org.joda.time.DateTime;
import java.util.ArrayList;
import java.util.Collection;
import com.google.gson.Gson;

import javax.annotation.Nullable;

import static com.google.common.collect.Collections2.filter;

public class Habits {

    ArrayList<Habit> dailyHabits = new ArrayList<Habit>();
    ArrayList<Habit> weeklyHabits = new ArrayList<Habit>();
    ArrayList<Habit> monthlyHabits = new ArrayList<Habit>();

    public ArrayList<Habit> getHabitsFor(Habit.TimeWindow timeWindow){
        if(timeWindow.equals(Habit.TimeWindow.DAILY)){
           return dailyHabits;
        } else if(timeWindow.equals(Habit.TimeWindow.WEEKLY)){
            return weeklyHabits;
        } else if(timeWindow.equals(Habit.TimeWindow.MONTHLY)){
            return monthlyHabits;
        } else {
            throw new RuntimeException("Unexpected timewindow type");
        }
    }

    public void setHabitsFor(Habit.TimeWindow timeWindow, ArrayList<Habit> habits){
        if(timeWindow.equals(Habit.TimeWindow.DAILY)){
            dailyHabits = habits;
        } else if(timeWindow.equals(Habit.TimeWindow.WEEKLY)){
            weeklyHabits = habits;
        } else if(timeWindow.equals(Habit.TimeWindow.MONTHLY)){
            monthlyHabits = habits;
        } else {
            throw new RuntimeException("Unexpected timewindow type");
        }
    }

    public Habits(){
        this.setHabitsFor(Habit.TimeWindow.DAILY, new ArrayList<Habit>());
        this.setHabitsFor(Habit.TimeWindow.WEEKLY, new ArrayList<Habit>());
        this.setHabitsFor(Habit.TimeWindow.MONTHLY, new ArrayList<Habit>());
    }

    // FIXME: clean this up a bit
    public static Habits getTestFixture(){
        Habits habits = new Habits();

        Habit.TimeWindow timeWindow;
        ArrayList<Habit> habitList;

        habitList = new ArrayList<Habit>();
        timeWindow = Habit.TimeWindow.DAILY;
        Habit addedYesterday = new Habit(Habit.Kind.GOOD, timeWindow, "Added yesterday");
        DateTime lastMidnight = DateTime.now().withTimeAtStartOfDay();
        addedYesterday.setLastTicked(lastMidnight.minusHours(1));
        Habit notDue = new Habit(Habit.Kind.GOOD, timeWindow, "Just added, not due");
        Habit dueSoon = new Habit(Habit.Kind.GOOD, timeWindow, "Due soon daily");
        dueSoon.setLastTicked(DateTime.now().minusHours(23).minusMinutes(59));
        Habit overdue = new Habit(Habit.Kind.GOOD, timeWindow, "Overdue daily");
        overdue.setLastTicked(DateTime.now().minusHours(25));
        habitList.add(addedYesterday); habitList.add(notDue); habitList.add(dueSoon); habitList.add(overdue);
        habits.setHabitsFor(timeWindow, habitList);

        habitList = new ArrayList<Habit>();
        timeWindow = Habit.TimeWindow.WEEKLY;
        notDue = new Habit(Habit.Kind.GOOD, timeWindow, "Not due weekly");
        dueSoon = new Habit(Habit.Kind.GOOD, timeWindow, "Due soon weekly");
        dueSoon.setLastTicked(DateTime.now().minusDays(6).minusHours(12));
        overdue = new Habit(Habit.Kind.GOOD, timeWindow, "Overdue weekly");
        overdue.setLastTicked(DateTime.now().minusDays(8));
        habitList.add(notDue); habitList.add(dueSoon); habitList.add(overdue);
        habits.setHabitsFor(timeWindow, habitList);

        habitList = new ArrayList<Habit>();
        timeWindow = Habit.TimeWindow.MONTHLY;
        notDue = new Habit(Habit.Kind.GOOD, timeWindow, "Not due monthly");
        dueSoon = new Habit(Habit.Kind.GOOD, timeWindow, "Due soon monthly");
        dueSoon.setLastTicked(DateTime.now().minusDays(29));
        overdue = new Habit(Habit.Kind.GOOD, timeWindow, "Overdue monthly");
        overdue.setLastTicked(DateTime.now().minusDays(40));
        habitList.add(notDue); habitList.add(dueSoon); habitList.add(overdue);
        habits.setHabitsFor(timeWindow, habitList);

        return habits;
    }

    public static String toJson(Habits habits){
        return new Gson().toJson(habits);
    }

    public static Habits fromJson(String json){
        return new Gson().fromJson(json, Habits.class);
    }

    public int countActionable(Habit.TimeWindow timeWindow){
        Collection elementsInTimeWindow = getHabitsFor(timeWindow);
        return filter(elementsInTimeWindow, new Predicate() {
            @Override
            public boolean apply(@Nullable Object o) {
                return ((Habit)o).canBeMarkedAsDoneAgain();
            }
        }).size();
    }



}

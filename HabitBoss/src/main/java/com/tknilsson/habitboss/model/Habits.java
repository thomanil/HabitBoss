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

        Habit overdueTickedTwoDaysAgo = new Habit(Habit.Kind.GOOD, timeWindow, "Daily exercise");
        overdueTickedTwoDaysAgo.setLastTicked(DateTime.now().minusDays(2));
        Habit tickedYesterdayCanBeTicked = new Habit(Habit.Kind.GOOD, timeWindow, "Walk the dog");
        DateTime lastMidnight = DateTime.now().withTimeAtStartOfDay();
        tickedYesterdayCanBeTicked.setLastTicked(lastMidnight.minusHours(1));
        Habit newDaily = new Habit(Habit.Kind.GOOD, timeWindow, "Floss");
        newDaily.markAsDone();
        habitList.add(overdueTickedTwoDaysAgo); habitList.add(tickedYesterdayCanBeTicked); habitList.add(newDaily);
        habits.setHabitsFor(timeWindow, habitList);

        habitList = new ArrayList<Habit>();
        timeWindow = Habit.TimeWindow.WEEKLY;
        Habit overdueTickedTwoWeeksAgo = new Habit(Habit.Kind.GOOD, timeWindow, "Weekly swim");
        overdueTickedTwoWeeksAgo.setLastTicked(DateTime.now().minusWeeks(2));
        Habit tickedLastWeekCanBeTicked = new Habit(Habit.Kind.GOOD, timeWindow, "Sweep the floor");
        tickedLastWeekCanBeTicked.setLastTicked(DateTime.now().minusWeeks(1));
        Habit weeklyJustTicked = new Habit(Habit.Kind.GOOD, timeWindow, "Wash clothes");
        weeklyJustTicked.markAsDone();
        habitList.add(overdueTickedTwoWeeksAgo); habitList.add(tickedLastWeekCanBeTicked); habitList.add(weeklyJustTicked);
        habits.setHabitsFor(timeWindow, habitList);

        habitList = new ArrayList<Habit>();
        timeWindow = Habit.TimeWindow.MONTHLY;
        Habit overdueTickedTwoMonthsAgo = new Habit(Habit.Kind.GOOD, timeWindow, "Monthly track meet");
        overdueTickedTwoMonthsAgo.setLastTicked(DateTime.now().minusMonths(2));
        Habit tickedLastMonthCanBeTicked = new Habit(Habit.Kind.GOOD, timeWindow, "Cut my hair");
        tickedLastMonthCanBeTicked.setLastTicked(DateTime.now().minusMonths(1));
        Habit monthlyJustTicked = new Habit(Habit.Kind.GOOD, timeWindow, "Check tire pressure");
        monthlyJustTicked.markAsDone();
        habitList.add(overdueTickedTwoMonthsAgo); habitList.add(tickedLastMonthCanBeTicked); habitList.add(monthlyJustTicked);
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

    public void failAllOVerdues(){
        failOverdues(dailyHabits);
        failOverdues(weeklyHabits);
        failOverdues(monthlyHabits);
    }

    private void failOverdues(ArrayList<Habit> habits){
        for (Habit habit : habits){
            if(habit.isOverdue()){
                habit.stopWinningStreak();
            }
        }
    }



}

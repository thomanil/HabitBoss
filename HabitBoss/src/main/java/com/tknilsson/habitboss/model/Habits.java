package com.tknilsson.habitboss.model;


import android.content.Context;

import com.google.common.base.Predicate;
import com.tknilsson.habitboss.ui.HabitListAdapter;
import com.tknilsson.habitboss.ui.HabitSectionFragment;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static com.google.common.collect.Collections2.*;

import javax.annotation.Nullable;

public class Habits {

    private static HashMap<Habit.TimeWindow, ArrayList<Habit>> habitLists = new HashMap<Habit.TimeWindow, ArrayList<Habit>>();

    private static boolean INIT_LISTS_WITH_FIXTURES = true;

    public static ArrayList<Habit> testFixture (Habit.TimeWindow timeWindow){
        ArrayList<Habit> habits = new ArrayList<Habit>();
        if(timeWindow.equals(Habit.TimeWindow.DAILY)){
            Habit addedYesterday = new Habit(Habit.Kind.GOOD, timeWindow, "Added yesterday");
            DateTime lastMidnight = DateTime.now().withTimeAtStartOfDay();
            addedYesterday.setLastTicked(lastMidnight.minusHours(1));
            Habit notDue = new Habit(Habit.Kind.GOOD, timeWindow, "Just added, not due");
            Habit dueSoon = new Habit(Habit.Kind.GOOD, timeWindow, "Due soon daily");
            dueSoon.setLastTicked(DateTime.now().minusHours(23).minusMinutes(59));
            Habit overdue = new Habit(Habit.Kind.GOOD, timeWindow, "Overdue daily");
            overdue.setLastTicked(DateTime.now().minusHours(25));
            habits.add(addedYesterday); habits.add(notDue); habits.add(dueSoon); habits.add(overdue);
        } else if(timeWindow.equals(Habit.TimeWindow.WEEKLY)){
            Habit notDue = new Habit(Habit.Kind.GOOD, timeWindow, "Not due weekly");
            Habit dueSoon = new Habit(Habit.Kind.GOOD, timeWindow, "Due soon weekly");
            dueSoon.setLastTicked(DateTime.now().minusDays(6).minusHours(12));
            Habit overdue = new Habit(Habit.Kind.GOOD, timeWindow, "Overdue weekly");
            overdue.setLastTicked(DateTime.now().minusDays(8));
            habits.add(notDue); habits.add(dueSoon); habits.add(overdue);
        } else if(timeWindow.equals(Habit.TimeWindow.MONTHLY)){
            Habit notDue = new Habit(Habit.Kind.GOOD, timeWindow, "Not due monthly");
            Habit dueSoon = new Habit(Habit.Kind.GOOD, timeWindow, "Due soon monthly");
            dueSoon.setLastTicked(DateTime.now().minusDays(29));
            Habit overdue = new Habit(Habit.Kind.GOOD, timeWindow, "Overdue monthly");
            overdue.setLastTicked(DateTime.now().minusDays(40));
            habits.add(notDue); habits.add(dueSoon); habits.add(overdue);
        }

        return habits;
    }

    public static ArrayList<Habit> newHabitList(Habit.TimeWindow timeWindow){
        if(INIT_LISTS_WITH_FIXTURES){
           return testFixture(timeWindow);
        } else {
            return new ArrayList<Habit>();
        }
    }

    public static HabitListAdapter getListAdapter(HabitSectionFragment parent, Context ctx, Habit.TimeWindow timeWindow){

        if(!habitLists.containsKey(timeWindow)){
            habitLists.put(timeWindow, newHabitList(timeWindow));
        }
        return new HabitListAdapter(parent, ctx, habitLists.get(timeWindow));
    }

    public static int countActionable(Habit.TimeWindow timeWindow){
        if(habitLists == null || habitLists.get(timeWindow) == null){
            return 0;
        }

        Collection habits = habitLists.get(timeWindow);

        return filter(habits, new Predicate() {
            @Override
            public boolean apply(@Nullable Object o) {
                return ((Habit)o).canBeMarkedAsDoneAgain();
            }
        }).size();
    }
}
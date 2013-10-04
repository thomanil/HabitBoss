package com.tknilsson.habitboss.model;


import android.content.Context;

import com.tknilsson.habitboss.ui.HabitListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Habits {

    private static HashMap<Habit.TimeWindow, ArrayList<Habit>> habitLists = new HashMap<Habit.TimeWindow, ArrayList<Habit>>();

    private static boolean INIT_LISTS_WITH_FIXTURES = true;

    public static ArrayList<Habit> testFixture (Habit.TimeWindow timeWindow){
        ArrayList<Habit> habits = new ArrayList<Habit>();
        habits.add(new Habit(Habit.Kind.GOOD, timeWindow, "Walk the dog"));
        habits.add(new Habit(Habit.Kind.GOOD, timeWindow, "Feed the cat"));
        habits.add(new Habit(Habit.Kind.GOOD, timeWindow, "Herd the sheep"));
        return habits;
    }

    public static ArrayList<Habit> newHabitList(Habit.TimeWindow timeWindow){
        if(INIT_LISTS_WITH_FIXTURES){
           return testFixture(timeWindow);
        } else {
            return new ArrayList<Habit>();
        }
    }

    public static HabitListAdapter getListAdapter(Context ctx, Habit.TimeWindow timeWindow){
        if(!habitLists.containsKey(timeWindow)){
            habitLists.put(timeWindow, newHabitList(timeWindow));
        }
        return new HabitListAdapter(ctx, habitLists.get(timeWindow));
    }



}

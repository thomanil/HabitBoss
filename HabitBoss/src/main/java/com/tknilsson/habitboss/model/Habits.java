package com.tknilsson.habitboss.model;


import android.content.Context;

import com.tknilsson.habitboss.ui.HabitListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Habits {

    // TODO overarching static methods to CRUD, update, check for overdue stuff etc.


    private static HashMap<Habit.TimeWindow, ArrayList<Habit>> habitLists = new HashMap<Habit.TimeWindow, ArrayList<Habit>>();

    public static HabitListAdapter getListAdapter(Context ctx, Habit.TimeWindow timeWindow){
        if(!habitLists.containsKey(timeWindow)){
            habitLists.put(timeWindow, new ArrayList<Habit>());
        }

        return new HabitListAdapter(ctx, habitLists.get(timeWindow));
    }

}

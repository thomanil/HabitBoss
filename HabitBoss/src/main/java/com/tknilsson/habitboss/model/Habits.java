package com.tknilsson.habitboss.model;

import android.content.Context;

import com.tknilsson.habitboss.ui.HabitListAdapter;

import java.util.ArrayList;

public class Habits {

    static ArrayList<Habit> currentHabits;

    // TODO overarching static methods to CRUD, update, check for overdue stuff etc.

    public static Habit addNewHabit(Habit.Kind kind, Habit.TimeWindow timeWindow, String description){
        Habit newHabit = new Habit(kind, timeWindow, description);
        currentHabits.add(newHabit);
        return newHabit;
    }

    public static void updateHabit(Habit habit){

    }

    public static ArrayList<Habit> getAll(Habit.Kind kind, Habit.TimeWindow timeWindow){
        return null;
    }

    public static void restoreFromDB(){
        // TODO
    }

    public static void saveToDb(){
        // TODO
    }


}

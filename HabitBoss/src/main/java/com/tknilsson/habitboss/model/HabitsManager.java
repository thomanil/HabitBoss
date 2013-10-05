package com.tknilsson.habitboss.model;

import android.content.Context;
import com.tknilsson.habitboss.ui.HabitListAdapter;

public class HabitsManager {

    private static Habits habits = new Habits();
    private static boolean USE_TEST_FIXTURE = true;

    static {
        if(USE_TEST_FIXTURE){
            habits = Habits.getTestFixture();
        }
    }

    public static HabitListAdapter getListAdapter(Context ctx, Habit.TimeWindow timeWindow){
        return new HabitListAdapter(ctx, habits.getHabitsFor(timeWindow));
    }

    public static int countActionable(Habit.TimeWindow timeWindow){
        if(habits == null || habits.getHabitsFor(timeWindow) == null){
            return 0;
        }

        return habits.countActionable(timeWindow);
    }

    public static void saveHabitsToInternalFile(){
        // Serialize habits to json, store to disk/shared pref?

        // Save to internal file:
        // http://developer.android.com/training/basics/data-storage/files.html#WriteInternalStorage
    }

    public static void loadHabitsFromInternalFile(){
        // Fetch string from to disk/shared pref, marshall habits from that
    }
}
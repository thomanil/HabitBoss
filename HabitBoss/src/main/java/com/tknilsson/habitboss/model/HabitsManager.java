package com.tknilsson.habitboss.model;

import android.content.Context;
import android.util.Log;

import com.tknilsson.habitboss.ui.HabitListAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class HabitsManager {

    private static Habits habits;

    private static boolean USE_TEST_FIXTURE = true;


    static {
        initHabits();
    }

    private static void initHabits(){
        if(USE_TEST_FIXTURE){
            habits = Habits.getTestFixture();
        } else {
            habits = new Habits();
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

    public static void failAllOverdues(){
        habits.failAllOVerdues();
    }

    private static final String SERIALIZED_FILE_NAME = "storedHabits.json";

    public static boolean isPreviousHabitsPersistedAsJson(Context ctx){
        return (ctx.getFileStreamPath(SERIALIZED_FILE_NAME).exists());
    }

    public static void deleteLocallyStoredHabits(Context ctx){
        if (isPreviousHabitsPersistedAsJson(ctx)){
            ctx.deleteFile(SERIALIZED_FILE_NAME);
        }
    }

    public static void resetHabits(Context ctx){
         HabitsManager.deleteLocallyStoredHabits(ctx);
         initHabits();
    }

    public static void saveHabitsToInternalFile(Context ctx){
        if(HabitsManager.isPreviousHabitsPersistedAsJson(ctx)){
            ctx.deleteFile(SERIALIZED_FILE_NAME);
        }

        try {
            FileOutputStream outputStream;
            outputStream = ctx.openFileOutput(SERIALIZED_FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(Habits.toJson(habits).getBytes());
            outputStream.close();
        } catch (Exception e) {
            Log.e("com.tknilsson.habitboss", "Failed to persist habits to local json file", e);
        }
    }

    public static void loadHabitsFromInternalFile(Context ctx){
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(ctx.openFileInput(SERIALIZED_FILE_NAME)));
            String inputString;
            StringBuffer stringBuffer = new StringBuffer();
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString + "\n");
            }
            habits = Habits.fromJson(stringBuffer.toString());
        } catch (Exception e) {
            Log.e("com.tknilsson.habitboss", "Failed to load habits from local json file, removing it, returning empty habits", e);

            ctx.deleteFile(SERIALIZED_FILE_NAME);
            habits = new Habits();
        }
    }
}
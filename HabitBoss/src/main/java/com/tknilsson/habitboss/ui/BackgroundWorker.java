package com.tknilsson.habitboss.ui;

import android.os.Handler;
import android.util.Log;

import com.tknilsson.habitboss.model.HabitsManager;


public class BackgroundWorker {

    final MainActivity mainActivity;

    int FIVE_SECONDS = 1000*5;
    int WORK_INTERVAL = FIVE_SECONDS;

    public BackgroundWorker(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public void start(){
        scheduleNext();
    };

    private void scheduleNext(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                work();
                scheduleNext();
            }
        }, WORK_INTERVAL);
    }

    private void work(){
        HabitsManager.failAllOverdues();
        mainActivity.refreshAllHabitPages();
        mainActivity.refreshTabTitles();
    }



}

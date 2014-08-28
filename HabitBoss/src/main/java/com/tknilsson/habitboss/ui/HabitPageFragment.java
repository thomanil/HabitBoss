package com.tknilsson.habitboss.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tknilsson.habitboss.R;
import com.tknilsson.habitboss.model.Habit;
import com.tknilsson.habitboss.model.HabitsManager;

public class HabitPageFragment extends Fragment {

    private Habit.TimeWindow timeWindow;

    public Habit.TimeWindow getTimeWindow() {
        return timeWindow;
    }

    public void setTimeWindow(Habit.TimeWindow timeWindow) {
        this.timeWindow = timeWindow;
    }



    public String getPageName(){
        int actionCount = HabitsManager.countActionable(getTimeWindow());
        if(actionCount == 0){
            return getTimeWindow().toString();
        } else {
            return getTimeWindow().toString()+" ("+actionCount+")";
        }
    }

    public HabitPageFragment() {

    }

    //public HabitPageFragment(Habit.TimeWindow timeWindow) {
    //
    //}

    public static Fragment getInstance(Habit.TimeWindow timeWindow) {
        HabitPageFragment habitPageFragment = new HabitPageFragment();
        habitPageFragment.setTimeWindow(timeWindow);
        return habitPageFragment;
    }

    private static String TIMEWINDOW_KEY = "timeWindow";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            String savedTimewindow = savedInstanceState.getString(TIMEWINDOW_KEY);
            if (savedTimewindow != null){
                setTimeWindow(Habit.TimeWindow.valueOf(savedTimewindow));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TIMEWINDOW_KEY, getTimeWindow().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.habit_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
    }

    HabitListAdapter doHabitAdapter = null;

    public void initUI(){
        doHabitAdapter = HabitsManager.getListAdapter(getActivity().getApplicationContext(), getTimeWindow());
        ListView doListView = (ListView) getView().findViewById(R.id.habit_making_list);
        doListView.setAdapter(doHabitAdapter);
        updateEditContextAwareElements(MainActivity.editingHabits);
    }

    public void updateEditContextAwareElements(boolean editingHabits){
        if (getView() != null && getView().findViewById(R.id.habit_adding) != null){
            if(!editingHabits){
                getView().findViewById(R.id.habit_adding).setVisibility(View.GONE);
            } else {
                getView().findViewById(R.id.habit_adding).setVisibility(View.VISIBLE);
            }
            doHabitAdapter.markHabitsChanged();
        }
    }

    public void addHabit(View view) {
        EditText newHabitText = ((EditText)getView().findViewById(R.id.new_habit_text));
        Habit habit = new Habit(getTimeWindow(), newHabitText.getEditableText().toString());
        doHabitAdapter.addNewHabit(habit);
        newHabitText.setText("");

        String feedback = view.getContext().getString(R.string.new_habit_added)+ " ";
        if(getTimeWindow().equals(Habit.TimeWindow.DAILY)){
            feedback = feedback + view.getContext().getString(R.string.daily_habit_added_feedback);
        } else if(getTimeWindow().equals(Habit.TimeWindow.WEEKLY)){
            feedback = feedback + view.getContext().getString(R.string.weekly_habit_added_feedback);
        } else if(getTimeWindow().equals(Habit.TimeWindow.MONTHLY)){
            feedback = feedback + view.getContext().getString(R.string.monthly_habit_added_feedback);
        } else {
            throw new RuntimeException("Unexpected timewindow type");
        }

        Toast.makeText(getActivity(), feedback, Toast.LENGTH_LONG).show();
    }


}

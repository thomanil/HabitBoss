package com.tknilsson.habitboss.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.tknilsson.habitboss.R;
import com.tknilsson.habitboss.model.Habit;
import com.tknilsson.habitboss.model.HabitsManager;

public class HabitSectionFragment extends Fragment {

    private Habit.TimeWindow timeWindow;

    public String getSectionName(){
        int actionCount = HabitsManager.countActionable(timeWindow);
        if(actionCount == 0){
            return timeWindow.toString();
        } else {
            return timeWindow.toString()+" ("+actionCount+")";
        }
    }

    public HabitSectionFragment(Habit.TimeWindow timeWindow) {
        this.timeWindow = timeWindow;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.habit_section, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    HabitListAdapter doHabitAdapter = null;

    public void initUI(){
        doHabitAdapter = HabitsManager.getListAdapter(getView().getContext(), timeWindow);
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
        EditText habitText = ((EditText)getView().findViewById(R.id.new_habit_text));
        Habit habit = new Habit(Habit.Kind.GOOD, timeWindow, habitText.getEditableText().toString());
        doHabitAdapter.addNewHabit(habit);
        habitText.setText("");
    }

}

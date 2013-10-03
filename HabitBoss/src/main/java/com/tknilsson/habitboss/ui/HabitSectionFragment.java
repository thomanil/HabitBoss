package com.tknilsson.habitboss.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.tknilsson.habitboss.R;

import java.util.ArrayList;

public class HabitSectionFragment extends Fragment {



    public HabitSectionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initModel();
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

    ArrayList<String> doItems = new ArrayList<String>();

    private void initModel(){
        doItems.add("Walk the dog");
        doItems.add("Feed the cat");
        doItems.add("Herd the sheep");
    }

    HabitListAdapter doHabitAdapter = null;

    private void initUI(){
        doHabitAdapter = new HabitListAdapter(getView().getContext(), doItems);
        ListView doListView = (ListView) getView().findViewById(R.id.habit_making_list);
        doListView.setAdapter(doHabitAdapter);
        updateEditContextAwareElements(MainActivity.editingHabits);
    }

    public void updateEditContextAwareElements(boolean editingHabits){
        if (getView() != null && getView().findViewById(R.id.habit_adding) != null){
            if(!editingHabits){
                getView().findViewById(R.id.habit_adding).setVisibility(View.INVISIBLE);
            } else {
                getView().findViewById(R.id.habit_adding).setVisibility(View.VISIBLE);
            }
            doHabitAdapter.notifyDataSetInvalidated();
        }
    }

    public void addHabit(View view) {
        EditText habitText = ((EditText)getView().findViewById(R.id.new_habit_text));
        doHabitAdapter.add(habitText.getEditableText().toString());
        habitText.setText("");
    }

}

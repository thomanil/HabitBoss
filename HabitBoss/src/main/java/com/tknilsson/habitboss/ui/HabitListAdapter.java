package com.tknilsson.habitboss.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tknilsson.habitboss.R;
import com.tknilsson.habitboss.model.Habit;

import java.util.ArrayList;

public class HabitListAdapter extends ArrayAdapter<Habit> {

    private final Context context;
    private final ArrayList<Habit> habits;

    public HabitListAdapter(Context context, ArrayList<Habit> habits) {
        super(context, R.layout.habit_line, habits);
        this.context = context;
        this.habits = habits;
    }

    public void addNewHabit(Habit habit){
        habits.add(habit);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.habit_line, parent, false);
        return initHabitRow(position, rowView);
    }

    private View initHabitRow(final int position, final View rowView){
        final Habit habit = habits.get(position);
        final HabitListAdapter adapter = this;

        TextView textView = (TextView) rowView.findViewById(R.id.habit_row_text);
        textView.setText(habit.getDescription());

        final Button doneBtn = (Button) rowView.findViewById(R.id.habit_done_button);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                habit.markAsDone();
                adapter.notifyDataSetInvalidated();
            }
        });

        final Button removeBtn = (Button) rowView.findViewById(R.id.habit_remove_button);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                habits.remove(position);
                adapter.notifyDataSetInvalidated();
            }
        });

        if (MainActivity.editingHabits){
            doneBtn.setVisibility(View.INVISIBLE);
        } else {
            removeBtn.setVisibility(View.INVISIBLE);
        }

        if(!habit.canBeMarkedAsDoneAgain()){
            doneBtn.setVisibility(View.INVISIBLE);
            textView.setTextColor(Color.GRAY);
        }

        return rowView;
    }

}

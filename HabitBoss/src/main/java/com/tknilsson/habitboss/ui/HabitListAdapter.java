package com.tknilsson.habitboss.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
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
        markHabitsChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.habit_line, parent, false);
        return initHabitRow(position, rowView);
    }

    public void markHabitsChanged(){
        this.notifyDataSetInvalidated();
        MainActivity.currentMainActivity.refreshTabTitles();
    }

    private View initHabitRow(final int position, final View rowView){
        final Habit habit = habits.get(position);
        final HabitListAdapter adapter = this;

        final Button doneBtn = (Button) rowView.findViewById(R.id.habit_done_button);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                habit.markAsDone();
                markHabitsChanged();
            }
        });

        final Button undoBtn = (Button) rowView.findViewById(R.id.habit_undo_button);
        undoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                habit.undo();
                markHabitsChanged();
            }
        });

        final Button removeBtn = (Button) rowView.findViewById(R.id.habit_remove_button);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                habits.remove(position);
                markHabitsChanged();
            }
        });

        TextView text = (TextView) rowView.findViewById(R.id.habit_row_text);

        if(habit.canBeMarkedAsDoneAgain()){
            String summary = habit.getDescription();
            text.setText(habit.getDescription());
            doneBtn.setVisibility(View.VISIBLE);
            undoBtn.setVisibility(View.GONE);
            if(habit.isOverdue()){
                text.setTextColor(Color.RED);
                summary = summary + " ("+context.getString(R.string.overdue)+")";
            } else {
                int ORANGE = Color.rgb(255, 94, 41);
                text.setTextColor(ORANGE);
            }
            text.setText(summary);
        } else {
            text.setText(habit.getDescription());
            doneBtn.setVisibility(View.INVISIBLE);
            text.setTextColor(Color.GRAY);
            text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        if (MainActivity.editingHabits){
            removeBtn.setVisibility(View.VISIBLE);
            undoBtn.setVisibility(View.VISIBLE);
            doneBtn.setVisibility(View.GONE);
        }

        return rowView;
    }

}

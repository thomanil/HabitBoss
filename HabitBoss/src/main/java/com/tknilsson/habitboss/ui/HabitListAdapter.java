package com.tknilsson.habitboss.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tknilsson.habitboss.R;
import com.tknilsson.habitboss.model.Habit;

import java.util.ArrayList;
import java.util.HashMap;

public class HabitListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> values;

    public HabitListAdapter(Context context, ArrayList<String> values) {
        super(context, R.layout.habit_line, values);
        this.context = context;
        this.values = values;
    }

    static private HashMap<Habit.TimeWindow, HabitListAdapter> adapters = new HashMap<Habit.TimeWindow, HabitListAdapter>();

    public static HabitListAdapter getAdapterFor(Habit.TimeWindow timeWindow, Context ctx){
        if(!adapters.containsKey(timeWindow)){
            adapters.put(timeWindow, new HabitListAdapter(ctx, new ArrayList<String>()));
        }
        return adapters.get(timeWindow);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.habit_line, parent, false);
        return initHabitRow(position, rowView);
    }

    private View initHabitRow(final int position, View rowView){
        TextView textView = (TextView) rowView.findViewById(R.id.habit_row_text);
        textView.setText(values.get(position));

        final Button doneBtn = (Button) rowView.findViewById(R.id.habit_done_button);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "TODO: set as done: "+position, Toast.LENGTH_SHORT).show();
            }
        });

        final Button removeBtn = (Button) rowView.findViewById(R.id.habit_remove_button);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "TODO: remove: "+position, Toast.LENGTH_SHORT).show();
            }
        });

        if (MainActivity.editingHabits){
            doneBtn.setVisibility(View.INVISIBLE);
        } else {
            removeBtn.setVisibility(View.INVISIBLE);
        }

        return rowView;
    }

}

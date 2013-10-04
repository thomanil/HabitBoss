package com.tknilsson.habitboss.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tknilsson.habitboss.R;
import com.tknilsson.habitboss.model.Habit;

import java.util.Locale;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    Fragment[] sections = new Fragment[] {new HabitSectionFragment(Habit.TimeWindow.DAILY),
            new HabitSectionFragment(Habit.TimeWindow.WEEKLY),
            new HabitSectionFragment(Habit.TimeWindow.MONTHLY)};

    Context ctx;

    public SectionsPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        this.ctx = ctx;
    }

    @Override
    public Fragment getItem(int position) {
        return sections[position];
    }

    @Override
    public int getCount() {
        return sections.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ((HabitSectionFragment) sections[position]).getSectionName();
    }

    public void updateEditAwareElementsonAllSections(boolean editingHabits){
        ((HabitSectionFragment) sections[0]).updateEditContextAwareElements(editingHabits);
        ((HabitSectionFragment) sections[1]).updateEditContextAwareElements(editingHabits);
        ((HabitSectionFragment) sections[2]).updateEditContextAwareElements(editingHabits);
    }

}
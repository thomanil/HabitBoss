package com.tknilsson.habitboss.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tknilsson.habitboss.model.Habit;

public class HabitPagesPagerAdapter extends FragmentPagerAdapter {

    Fragment[] pages = new Fragment[] {HabitPageFragment.getInstance(Habit.TimeWindow.DAILY),
            HabitPageFragment.getInstance(Habit.TimeWindow.WEEKLY),
            HabitPageFragment.getInstance(Habit.TimeWindow.MONTHLY)};

    Context ctx;

    public HabitPagesPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        this.ctx = ctx;
    }

    @Override
    public Fragment getItem(int position) {
        return pages[position];
    }

    @Override
    public int getCount() {
        return pages.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ((HabitPageFragment) pages[position]).getPageName();
    }

    public void updateEditAwareElementsonAllPages(boolean editingHabits){
        ((HabitPageFragment) pages[0]).updateEditContextAwareElements(editingHabits);
        ((HabitPageFragment) pages[1]).updateEditContextAwareElements(editingHabits);
        ((HabitPageFragment) pages[2]).updateEditContextAwareElements(editingHabits);
    }

}
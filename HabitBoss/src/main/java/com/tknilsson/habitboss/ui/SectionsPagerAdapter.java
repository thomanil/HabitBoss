package com.tknilsson.habitboss.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tknilsson.habitboss.R;

import java.util.Locale;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    Fragment[] sections = new Fragment[] {new HabitSectionFragment(), new HabitSectionFragment(), new HabitSectionFragment()};
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
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return ctx.getString(R.string.title_daily).toUpperCase(l);
            case 1:
                return ctx.getString(R.string.title_weekly).toUpperCase(l);
            case 2:
                return ctx.getString(R.string.title_monthly).toUpperCase(l);
        }
        return null;
    }



    public void updateEditAwareElementsonAllSections(boolean editingHabits){
        ((HabitSectionFragment) sections[0]).updateEditContextAwareElements(editingHabits);
        ((HabitSectionFragment) sections[1]).updateEditContextAwareElements(editingHabits);
        ((HabitSectionFragment) sections[2]).updateEditContextAwareElements(editingHabits);
    }

}
package com.tknilsson.habitboss.ui;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.tknilsson.habitboss.R;
import com.tknilsson.habitboss.model.HabitsManager;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the pages. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    HabitPagesPagerAdapter mHabitPagesPagerAdapter;

    /**
     * The {@link ViewPager} that will host the page contents.
     */
    ViewPager mViewPager;

    private ArrayList<ActionBar.Tab> tabs = new ArrayList<ActionBar.Tab>();
    private Menu currentMenuOptions;

    public static MainActivity currentMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(HabitsManager.isPreviousHabitsPersistedAsJson(this)){
            HabitsManager.loadHabitsFromInternalFile(this);
        }
        initActionBarAndTabs();
        currentMainActivity = this;
        (new BackgroundWorker(this)).start();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        HabitsManager.saveHabitsToInternalFile(this);
    }

    private void initActionBarAndTabs(){

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary pages of the app.
        mHabitPagesPagerAdapter = new HabitPagesPagerAdapter(getSupportFragmentManager(), this);

        // Set up the ViewPager with the pages adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mHabitPagesPagerAdapter);

        // Force all 3 pages to init/load at once, not just current and adjacent
        mViewPager.setOffscreenPageLimit(2);

        // When swiping between different pages, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the pages in the app, add a tab to the action bar.
        for (int i = 0; i < mHabitPagesPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            tabs.add(actionBar.newTab()
                    .setText(mHabitPagesPagerAdapter.getPageTitle(i))
                    .setTabListener(this));

            actionBar.addTab(tabs.get(i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        currentMenuOptions = menu;
        initEditToggleAction();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.toggle_edit_mode:
                toggleEditMode();
                return true;
            case R.id.reset_habits:
                resetIfUserConfirms();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void resetIfUserConfirms(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.reset_dialog_title)
                .setMessage(R.string.reset_dialog_message)
                .setPositiveButton(R.string.reset_dialog_confirm, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetHabits();
                    }
                })
                .setNegativeButton(R.string.reset_dialog_cancel, null)
                .show();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    // Just use a singleton for storing/sharing edit state
    // (no need to make the "editing" state very persistent)
    public static boolean editingHabits = false;

    private void initEditToggleAction(){
        String editToggleText;
        if (!editingHabits){
            editToggleText = getString(R.string.start_edit);
        } else {
            editToggleText = getString(R.string.finish_edit);
        }

        currentMenuOptions.findItem(R.id.toggle_edit_mode).setTitle(editToggleText);
    }

    private void toggleEditMode(){
        editingHabits = !editingHabits;
        initEditToggleAction();
        HabitPagesPagerAdapter pagerAdapter = (HabitPagesPagerAdapter) mViewPager.getAdapter();
        pagerAdapter.updateEditAwareElementsonAllPages(editingHabits);
        refreshTabTitles();
    }

    private void resetHabits(){
        HabitsManager.resetHabits(this);
        refreshAllHabitPages();
    }

    public void refreshTabTitles(){
        if(tabs.size() == 3){
            tabs.get(0).setText(mHabitPagesPagerAdapter.getPageTitle(0));
            tabs.get(1).setText(mHabitPagesPagerAdapter.getPageTitle(1));
            tabs.get(2).setText(mHabitPagesPagerAdapter.getPageTitle(2));
        }
        mViewPager.invalidate();
    }

    public void refreshAllHabitPages(){
        ((HabitPageFragment) mHabitPagesPagerAdapter.getItem(0)).initUI();
        ((HabitPageFragment) mHabitPagesPagerAdapter.getItem(1)).initUI();
        ((HabitPageFragment) mHabitPagesPagerAdapter.getItem(2)).initUI();
        refreshTabTitles();
    }

    public void addHabit(View view) {
        getCurrentPageFragment().addHabit(view);
        hideKeyboard(view);
    }

    private HabitPageFragment getCurrentPageFragment(){
        HabitPagesPagerAdapter pagerAdapter = (HabitPagesPagerAdapter) mViewPager.getAdapter();
        return (HabitPageFragment) pagerAdapter.getItem(mViewPager.getCurrentItem());
    }

    private void hideKeyboard(View view){
        InputMethodManager inputManager =
                (InputMethodManager) view.getContext().
                        getSystemService(view.getContext().INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }


}

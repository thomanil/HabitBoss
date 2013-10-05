package com.tknilsson.habitboss.model;

import junit.framework.Assert;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeUtils;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.util.HashMap;
import java.util.ArrayList;


import java.lang.RuntimeException;

import static org.junit.Assert.*;

public class HabitsTest  {

    // TODO make the ugly Habits/model stuff more testable
    // make init with/without fixtures a single initial step in Habits
    // Rename Habits to HabitsManager
    // Extract habits map structure into separate Habits data structure

    public void testModelToJson() {
       Habits.initHabitStateWithFixtures();
       HashMap<Habit.TimeWindow, ArrayList<Habit>> model = Habits.getHabitLists();
       String json = Habits.toJson(model);
        // TODO check json
    }

    @Test
    public void testModelFromJson(){
        String json = "";
        HashMap<Habit.TimeWindow, ArrayList<Habit>> model = Habits.fromJson(json);
        // TODO check model
    }

}
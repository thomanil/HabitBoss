package com.tknilsson.habitboss.model;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

public class HabitTest {

    @Test
    public void testInstantiation() {
       Habit habit = new Habit(Habit.Kind.GOOD, Habit.TimeWindow.DAILY, "Walk the dog");
       Assert.assertNotNull(habit);
       Assert.assertFalse(habit.isOverdue());
       Assert.assertFalse(habit.isSoonDue());
       Assert.assertEquals("Walk the dog", habit.getDescription());
    }

}
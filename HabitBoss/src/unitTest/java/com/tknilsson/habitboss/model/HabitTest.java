package com.tknilsson.habitboss.model;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

public class HabitTest {

    @Test
    public void testPresent() {
       Habit habit = new Habit(Habit.Kind.GOOD, Habit.TimeWindow.DAILY);
       Assert.assertNotNull(habit);
       Assert.assertFalse(habit.isOverdue());
       Assert.assertFalse(habit.isSoonDue());
    }

}
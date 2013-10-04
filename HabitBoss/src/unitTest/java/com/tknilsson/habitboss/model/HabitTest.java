package com.tknilsson.habitboss.model;

import com.tknilsson.habitboss.model.Habit;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

public class HabitTest {

    @Test
    public void testPresent() {
       Habit habit = new Habit();
       Assert.assertNotNull(habit);
       Assert.assertFalse(habit.isOverdue());
    }




}
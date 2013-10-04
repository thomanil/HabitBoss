package com.tknilsson.habitboss.model;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

public class HabitTest {

    @Test
    public void testPresent() {
       GoodHabit habit = new GoodHabit();
       Assert.assertNotNull(habit);
       Assert.assertFalse(habit.overdue());
    }




}
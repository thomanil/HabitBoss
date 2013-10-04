package com.tknilsson.habitboss.model;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.*;

public class HabitTest {

    @Test
    public void testInstantiation() {
       Habit habit = new Habit(Habit.Kind.GOOD, Habit.TimeWindow.DAILY, "Walk the dog");
       Assert.assertNotNull(habit);
       Assert.assertEquals("Walk the dog", habit.getDescription());
    }

    @Test
    public void testNewlyCreatedHabitsShouldNotBeOverdue() {
        Habit dailyHabit = new Habit(Habit.Kind.GOOD, Habit.TimeWindow.DAILY, "");
        Assert.assertFalse(dailyHabit.isOverdue());

        Habit weeklyHabit = new Habit(Habit.Kind.GOOD, Habit.TimeWindow.WEEKLY, "");
        Assert.assertFalse(weeklyHabit.isOverdue());

        Habit monthlyHabit = new Habit(Habit.Kind.GOOD, Habit.TimeWindow.MONTHLY, "");
        Assert.assertFalse(monthlyHabit.isOverdue());
    }

    @Test
    public void testAllHabitsOverdueOncePastLengthofTimeWindow() {
        Habit dailyHabit = new Habit(Habit.Kind.GOOD, Habit.TimeWindow.DAILY, "");
        dailyHabit.setLastTicked(DateTime.now().minusHours(2));
        Assert.assertFalse(dailyHabit.isOverdue());
        dailyHabit.setLastTicked(DateTime.now().minusDays(2));
        Assert.assertTrue(dailyHabit.isOverdue());
        dailyHabit.setLastTicked(DateTime.now().minusMonths(2));
        Assert.assertTrue(dailyHabit.isOverdue());

        Habit weeklyHabit = new Habit(Habit.Kind.GOOD, Habit.TimeWindow.WEEKLY, "");
        weeklyHabit.setLastTicked(DateTime.now().minusHours(2));
        Assert.assertFalse(weeklyHabit.isOverdue());
        weeklyHabit.setLastTicked(DateTime.now().minusDays(2));
        Assert.assertFalse(weeklyHabit.isOverdue());
        weeklyHabit.setLastTicked(DateTime.now().minusMonths(2));
        Assert.assertTrue(weeklyHabit.isOverdue());

        Habit monthlyHabit = new Habit(Habit.Kind.GOOD, Habit.TimeWindow.MONTHLY, "");
        monthlyHabit.setLastTicked(DateTime.now().minusHours(2));
        Assert.assertFalse(monthlyHabit.isOverdue());
        monthlyHabit.setLastTicked(DateTime.now().minusDays(2));
        Assert.assertFalse(monthlyHabit.isOverdue());
        monthlyHabit.setLastTicked(DateTime.now().minusMonths(2));
        Assert.assertTrue(monthlyHabit.isOverdue());
    }


    @Test
    public void testDailyDueSoonOnceWithinAnHourOfDueTime() {
        Habit habit = new Habit(Habit.Kind.GOOD, Habit.TimeWindow.DAILY, "");

        habit.setLastTicked(DateTime.now().minusHours(300));
        Assert.assertFalse(habit.isSoonDue());

        habit.setLastTicked(DateTime.now().minusHours(10));
        Assert.assertFalse(habit.isSoonDue());

        habit.setLastTicked(DateTime.now().minusMinutes(30));
        Assert.assertTrue(habit.isSoonDue());
    }

    @Test
    public void testWeeklyDueSoonWhenWithinADayOfDueTime() {
        Habit habit = new Habit(Habit.Kind.GOOD, Habit.TimeWindow.WEEKLY, "");

        habit.setLastTicked(DateTime.now().minusDays(300));
        Assert.assertFalse(habit.isSoonDue());

        habit.setLastTicked(DateTime.now().minusDays(10));
        Assert.assertFalse(habit.isSoonDue());

        habit.setLastTicked(DateTime.now().minusHours(10));
        Assert.assertTrue(habit.isSoonDue());
    }

    @Test
    public void testMonthlyDueSoonWhenWithinADayOfDueTime() {
        Habit habit = new Habit(Habit.Kind.GOOD, Habit.TimeWindow.MONTHLY, "");

        habit.setLastTicked(DateTime.now().minusDays(300));
        Assert.assertFalse(habit.isSoonDue());

        habit.setLastTicked(DateTime.now().minusDays(10));
        Assert.assertFalse(habit.isSoonDue());

        habit.setLastTicked(DateTime.now().minusHours(10));
        Assert.assertTrue(habit.isSoonDue());
    }

}
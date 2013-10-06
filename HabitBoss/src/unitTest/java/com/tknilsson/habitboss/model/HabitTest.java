package com.tknilsson.habitboss.model;

import com.tknilsson.habitboss.model.Habit;

import junit.framework.Assert;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeUtils;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;


import java.lang.RuntimeException;

import static org.junit.Assert.*;

public class HabitTest  {

    @Before
    public void setup(){
        DateTimeUtils.setCurrentMillisSystem();
    }

    @After
    public void teardown(){
        DateTimeUtils.setCurrentMillisSystem();
    }

    @Test
    public void testInstantiation() {
       Habit habit = new Habit(Habit.TimeWindow.DAILY, "Walk the dog");
       Assert.assertNotNull(habit);
       Assert.assertTrue(habit.getDescription().contains("Walk the dog"));
    }

    // TODO back later
    /*
    @Test
    public void testNewlyCreatedHabitsShouldNotBeOverdue() {
        Habit dailyHabit = new Habit(Habit.TimeWindow.DAILY, "");
        Assert.assertFalse(dailyHabit.isOverdue());

        Habit weeklyHabit = new Habit(Habit.TimeWindow.WEEKLY, "");
        Assert.assertFalse(weeklyHabit.isOverdue());

        Habit monthlyHabit = new Habit(Habit.TimeWindow.MONTHLY, "");
        Assert.assertFalse(monthlyHabit.isOverdue());
    }*/


        //String fmt = "HH:mm dd/MM";

        DateTime midDayMidWeekMidMonth = DateTime.now().withDayOfMonth(15).withDayOfWeek(3).withHourOfDay(12);
        DateTime now = midDayMidWeekMidMonth; //System.out.println(" now (mid day/week/month) : " + now.toString(fmt));
        //System.out.println("\n");

        DateTime dayBeforeLast = now.minusDays(2); //System.out.println(" dayBeforeLast : " + dayBeforeLast.toString(fmt));
        DateTime yesterday = now.minusDays(1); //System.out.println(" yesterday : " + yesterday.toString(fmt));
        DateTime comingMidnight = now.withTimeAtStartOfDay().plusHours(24);//System.out.println(" comingMidnight : " + comingMidnight.toString(fmt));
        DateTime tomorrow = now.plusDays(1); //System.out.println(" tomorrow : " + tomorrow.toString(fmt));
        //System.out.println("\n");

        DateTime weekBeforeLast = now.minusDays(10); //System.out.println(" weekBeforeLast : " + weekBeforeLast.toString(fmt));
        DateTime lastWeek = now.minusDays(7); //System.out.println(" lastWeek : " + lastWeek.toString(fmt));
        DateTime comingSunday = now.withDayOfWeek(7); //System.out.println(" comingSunday : " + comingSunday.toString(fmt));
        DateTime nextWeek = now.plusDays(7); //System.out.println(" nextWeek : " + nextWeek.toString(fmt));
        //System.out.println("\n");

        DateTime monthBeforeLast = now.minusDays(50); //System.out.println(" monthBeforeLast : " + monthBeforeLast.toString(fmt));
        DateTime lastMonth = now.minusDays(30); //System.out.println(" lastMonth : " + lastMonth.toString(fmt));
        DateTime lastDayOfMonth = now.dayOfMonth().withMaximumValue(); //System.out.println(" lastDayOfMonth : " + lastDayOfMonth.toString(fmt));
        DateTime nextMonth = now.plusDays(30); //System.out.println(" nextMonth : " + nextMonth.toString(fmt));
        //System.out.println("\n");


    @Test
    public void testDailyOverdue(){
        Habit habit = new Habit(Habit.TimeWindow.DAILY, "");
        DateTimeUtils.setCurrentMillisFixed(now.getMillis());

        habit.setLastTicked(yesterday);
        Assert.assertFalse(habit.isOverdue());

        habit.setLastTicked(dayBeforeLast);
        Assert.assertTrue(habit.isOverdue());
    }

    @Test
    public void testWeeklyOverdue(){
        Habit habit = new Habit(Habit.TimeWindow.WEEKLY, "");
        DateTimeUtils.setCurrentMillisFixed(now.getMillis());

        habit.setLastTicked(lastWeek);
        Assert.assertFalse(habit.isOverdue());

        habit.setLastTicked(weekBeforeLast);
        Assert.assertTrue(habit.isOverdue());
    }

    @Test
    public void testMonthlyOverdue(){
        Habit habit = new Habit(Habit.TimeWindow.MONTHLY, "");
        DateTimeUtils.setCurrentMillisFixed(now.getMillis());

        habit.setLastTicked(lastMonth);
        Assert.assertFalse(habit.isOverdue());

        habit.setLastTicked(monthBeforeLast);
        Assert.assertTrue(habit.isOverdue());
    }



    @Test
    public void testDailyMarkable(){
        Habit habit = new Habit(Habit.TimeWindow.DAILY, "");
        DateTimeUtils.setCurrentMillisFixed(now.getMillis());

        habit.setLastTicked(now.minusHours(1));
        Assert.assertFalse(habit.canBeMarkedAsDoneAgain());

        habit.setLastTicked(yesterday);
        Assert.assertTrue(habit.canBeMarkedAsDoneAgain());
    }

    @Test
    public void testWeeklyMarkable(){
        Habit habit = new Habit(Habit.TimeWindow.WEEKLY, "");
        DateTimeUtils.setCurrentMillisFixed(now.getMillis());

        habit.setLastTicked(now.minusHours(1));
        Assert.assertFalse(habit.canBeMarkedAsDoneAgain());

        habit.setLastTicked(yesterday);
        Assert.assertFalse(habit.canBeMarkedAsDoneAgain());

        habit.setLastTicked(lastWeek);
        Assert.assertTrue(habit.canBeMarkedAsDoneAgain());
    }

    @Test
    public void testMonthlyMarkable(){
        Habit habit = new Habit(Habit.TimeWindow.MONTHLY, "");
        DateTimeUtils.setCurrentMillisFixed(now.getMillis());

        habit.setLastTicked(now.minusHours(1));
        Assert.assertFalse(habit.canBeMarkedAsDoneAgain());

        habit.setLastTicked(yesterday);
        Assert.assertFalse(habit.canBeMarkedAsDoneAgain());

        habit.setLastTicked(lastWeek);
        Assert.assertFalse(habit.canBeMarkedAsDoneAgain());

        habit.setLastTicked(lastMonth);
        Assert.assertTrue(habit.canBeMarkedAsDoneAgain());
    }



    @Test
    public void testDailyTimeRemaining(){

    }

    @Test
    public void testWeeklyTimeRemaining(){

    }

    @Test
    public void testMonthlyTimeRemaining(){

    }








    /*
    @Test
    public void testAllHabitsOverdueOncePastLengthofTimeWindow() {
        Habit dailyHabit = new Habit(Habit.TimeWindow.DAILY, "");
        dailyHabit.setLastTicked(DateTime.now().minusHours(2));
        Assert.assertFalse(dailyHabit.isOverdue());
        dailyHabit.setLastTicked(DateTime.now().minusDays(2));
        Assert.assertTrue(dailyHabit.isOverdue());
        dailyHabit.setLastTicked(DateTime.now().minusMonths(2));
        Assert.assertTrue(dailyHabit.isOverdue());

        Habit weeklyHabit = new Habit(Habit.TimeWindow.WEEKLY, "");
        weeklyHabit.setLastTicked(DateTime.now().minusHours(2));
        Assert.assertFalse(weeklyHabit.isOverdue());
        weeklyHabit.setLastTicked(DateTime.now().minusDays(2));
        Assert.assertFalse(weeklyHabit.isOverdue());
        weeklyHabit.setLastTicked(DateTime.now().minusMonths(2));
        Assert.assertTrue(weeklyHabit.isOverdue());

        Habit monthlyHabit = new Habit(Habit.TimeWindow.MONTHLY, "");
        monthlyHabit.setLastTicked(DateTime.now().minusHours(2));
        Assert.assertFalse(monthlyHabit.isOverdue());
        monthlyHabit.setLastTicked(DateTime.now().minusDays(2));
        Assert.assertFalse(monthlyHabit.isOverdue());
        monthlyHabit.setLastTicked(DateTime.now().minusMonths(2));
        Assert.assertTrue(monthlyHabit.isOverdue());
    }


    @Test
    public void testDailyDueSoonOnceWithinAnHourOfDueTime() {
        Habit habit = new Habit(Habit.TimeWindow.DAILY, "");

        habit.setLastTicked(DateTime.now().minusMinutes(30));
        Assert.assertFalse(habit.isSoonDue());

        habit.setLastTicked(DateTime.now().minusHours(10));
        Assert.assertFalse(habit.isSoonDue());

        habit.setLastTicked(DateTime.now().minusHours(23).minusMinutes(30));
        Assert.assertTrue(habit.isSoonDue());
    }


    @Test
    public void testWeeklyDueSoonWhenWithinADayOfDueTime() {
        Habit habit = new Habit(Habit.TimeWindow.WEEKLY, "");

        habit.setLastTicked(DateTime.now().minusDays(1));
        Assert.assertFalse(habit.isSoonDue());

        habit.setLastTicked(DateTime.now().minusDays(5));
        Assert.assertFalse(habit.isSoonDue());

        habit.setLastTicked(DateTime.now().minusDays(6).minusHours(12));
        Assert.assertTrue(habit.isSoonDue());
    }

    @Test
    public void testMonthlyDueSoonWhenWithinAThreeDaysOfDueTime() {
        Habit habit = new Habit(Habit.TimeWindow.MONTHLY, "");

        habit.setLastTicked(DateTime.now().minusDays(25));
        Assert.assertFalse(habit.isSoonDue());

        habit.setLastTicked(DateTime.now().minusDays(10));
        Assert.assertFalse(habit.isSoonDue());

        habit.setLastTicked(DateTime.now().minusDays(29));
        Assert.assertTrue(habit.isSoonDue());
    }

    @Test
    public void testSetHabitDone() {
        Habit dailyHabit = new Habit(Habit.TimeWindow.DAILY, "");
        dailyHabit.setLastTicked(DateTime.now().minusMonths(2));
        Assert.assertTrue(dailyHabit.isOverdue());
        dailyHabit.markAsDone();
        Assert.assertFalse(dailyHabit.isOverdue());
        Assert.assertFalse(dailyHabit.isSoonDue());
    }

    @Test
    public void testWhenIsDailyMarkableAgain() {
        DateTime midnightToday = DateTime.now().withTimeAtStartOfDay();
        DateTime twohoursBeforeMidnight = midnightToday.minusHours(2);
        DateTime hourBeforeMidnight = midnightToday.minusHours(1);
        DateTime hourAfterMidnight =  midnightToday.plusHours(1);

        Habit markable = new Habit(Habit.TimeWindow.DAILY, "");
        markable.setLastTicked(twohoursBeforeMidnight);
        DateTimeUtils.setCurrentMillisFixed(hourAfterMidnight.getMillis());
        Assert.assertTrue(markable.canBeMarkedAsDoneAgain());

        Habit nonMarkable = new Habit(Habit.TimeWindow.DAILY, "");
        nonMarkable.setLastTicked(twohoursBeforeMidnight);
        DateTimeUtils.setCurrentMillisFixed(hourBeforeMidnight.getMillis());
        Assert.assertFalse(nonMarkable.canBeMarkedAsDoneAgain());
    }

    @Test
    public void testWhenIsWeeklyMarkableAgain() {
        DateTime sundayThisWeek = DateTime.now().withDayOfWeek(DateTimeConstants.SUNDAY);
        DateTime twoDaysBeforeSunday = sundayThisWeek.minusDays(2);
        DateTime oneDayBeforeSunday = sundayThisWeek.minusDays(1);
        DateTime oneDayAfterSunday =  sundayThisWeek.plusDays(1);

        Habit markable = new Habit(Habit.TimeWindow.WEEKLY, "");
        markable.setLastTicked(twoDaysBeforeSunday);
        DateTimeUtils.setCurrentMillisFixed(oneDayAfterSunday.getMillis());
        Assert.assertTrue(markable.canBeMarkedAsDoneAgain());

        Habit nonMarkable = new Habit(Habit.TimeWindow.WEEKLY, "");
        nonMarkable.setLastTicked(twoDaysBeforeSunday);
        DateTimeUtils.setCurrentMillisFixed(oneDayBeforeSunday.getMillis());
        Assert.assertFalse(nonMarkable.canBeMarkedAsDoneAgain());
    }

    @Test
    public void testWhenIsMonthlyMarkableAgain() {
        DateTime endOfLastMonth = new DateTime().minusMonths(1).dayOfMonth().withMaximumValue();
        DateTime twoDaysBeforeMonthEnd = endOfLastMonth.minusDays(2);
        DateTime oneDayBeforeMonthEnd = endOfLastMonth.minusDays(1);
        DateTime oneDayAfterMonthEnd =  endOfLastMonth.plusDays(1);

        Habit markable = new Habit(Habit.TimeWindow.MONTHLY, "");
        markable.setLastTicked(twoDaysBeforeMonthEnd);
        DateTimeUtils.setCurrentMillisFixed(oneDayAfterMonthEnd.getMillis());
        Assert.assertTrue(markable.canBeMarkedAsDoneAgain());

        Habit nonMarkable = new Habit(Habit.TimeWindow.MONTHLY, "");
        nonMarkable.setLastTicked(twoDaysBeforeMonthEnd);
        DateTimeUtils.setCurrentMillisFixed(oneDayBeforeMonthEnd.getMillis());
        Assert.assertFalse(nonMarkable.canBeMarkedAsDoneAgain());
    }
*/
}

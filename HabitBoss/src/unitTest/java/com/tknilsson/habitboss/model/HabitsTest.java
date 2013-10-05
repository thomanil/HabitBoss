package com.tknilsson.habitboss.model;

import junit.framework.Assert;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeUtils;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;


import java.lang.RuntimeException;

import static org.junit.Assert.*;

public class HabitsTest  {

    public void testModelToJson() {
        Habits model = Habits.getTestFixture();
        String json = Habits.toJson(model);
        // TODO check json
    }

    @Test
    public void testModelFromJson(){
        String json = "";
        Habits model = Habits.fromJson(json);
        // TODO check model
    }

    @Test
    public void testActionable(){
        // TODO just use fixture, check that it holds, and that it changes when state is changed
    }

}
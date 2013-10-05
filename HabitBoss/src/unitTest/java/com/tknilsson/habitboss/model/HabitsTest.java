package com.tknilsson.habitboss.model;

import com.google.common.base.Objects;

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

    @Test
    public void testModelToAndFromJson() {
        Habits originalModel = Habits.getTestFixture();
        String jsonRepresentation = Habits.toJson(originalModel);
        Habits remarshalledModel = Habits.fromJson(jsonRepresentation);
        Objects.equal(originalModel, remarshalledModel);
    }

    @Test
    public void testJsonToAndFromModel() {
        String originalJson = Habits.toJson(Habits.getTestFixture());
        Habits marshalledModel  = Habits.fromJson(originalJson);
        String reserializedJson = Habits.toJson(marshalledModel);
        Objects.equal(originalJson, reserializedJson);
    }

    @Test
    public void testActionable(){
        // TODO just use fixture, check that it holds, and that it changes when state is changed
    }

    @Test
    public void testGetDueDate(){
        // TODO
    }

    @Test
    public void testGetShorthandTimeTillDue(){
        // TODO
    }

}
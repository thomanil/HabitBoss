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
        Habits original = Habits.getTestFixture();
        String jsonRepresentation = Habits.toJson(original);
        Habits remarshalled = Habits.fromJson(jsonRepresentation);
        Objects.equal(original, remarshalled);
    }

    // TODO also add a test which does the inverse: json->model->json

    @Test
    public void testActionable(){
        // TODO just use fixture, check that it holds, and that it changes when state is changed
    }

}
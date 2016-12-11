package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import  org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by unazi on 11/1/16.
 */
public class BuggyClassTestSC1b {

    private BuggyClass myBuggyClass;

    @Before
    public  void setUp(){
        myBuggyClass = new BuggyClass();
    }

    @After
    public void tearDown(){
        myBuggyClass = null;
    }

    @Test
    public void testBuggyMethod1(){
        assertEquals(2, myBuggyClass.buggyMethod1(2,4),0.001);
    }

    @Test
    public void testBuggyMethod2(){
        assertEquals(-1, myBuggyClass.buggyMethod1(2,-3),0.001);
    }
}

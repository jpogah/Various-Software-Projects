package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by unazi on 11/1/16.
 */
public class BuggyClassTestSC1a {
    private BuggyClass myBuggyClass;

    @Before
    public  void setUp(){
        myBuggyClass = new BuggyClass();
    }

    @After
    public void tearDown(){
        myBuggyClass = null;
    }

    @Test(expected = ArithmeticException.class)
    public void testBuggyMethod1(){
        assertEquals(0, myBuggyClass.buggyMethod1(0,5),0.001);
    }

}

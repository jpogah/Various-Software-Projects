package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by unazi on 11/3/16.
 */
public class BuggyClassTestSC4 {
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
        int result= myBuggyClass.buggyMethod4(0,5);
    }

    @Test
    public void testBuggyMethod2(){
        assertEquals(6, myBuggyClass.buggyMethod4(2,-3),0.001);
    }



}

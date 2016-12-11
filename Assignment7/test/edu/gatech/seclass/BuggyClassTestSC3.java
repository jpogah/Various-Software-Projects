package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by unazi on 11/2/16.
 */
public class BuggyClassTestSC3 {
    private BuggyClass myBuggyClass;

    @Before
    public  void setUp(){
        myBuggyClass = new BuggyClass();
    }

    @After
    public void tearDown(){
        myBuggyClass = null;
    }

    @Test(expected=ArithmeticException.class)
    public void testBuggyMethod1(){
        int result = myBuggyClass.buggyMethod3(0,5);
    }

    @Test
    public void testBuggyMethod2(){
        assertEquals(1, myBuggyClass.buggyMethod3(2,-1),0.001);
    }

   @Test
    public void testBuggyMethod2(){
        assertEquals(8, myBuggyClass.buggyMethod3(2,16),0.001);
    }



}

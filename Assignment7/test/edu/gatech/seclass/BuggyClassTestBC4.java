package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by unazi on 11/3/16.
 */
public class BuggyClassTestBC4 {
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
        assertEquals(6, myBuggyClass.buggyMethod4(2,12),0.001);
    }

    @Test
    public void testBuggyMethod2(){
        assertEquals(6, myBuggyClass.buggyMethod4(2,-2),0.001);
    }



}

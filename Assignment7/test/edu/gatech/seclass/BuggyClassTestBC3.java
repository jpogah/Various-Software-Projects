package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by unazi on 11/2/16.
 */
public class BuggyClassTestBC3 {
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
        assertEquals(5, myBuggyClass.buggyMethod3(2,10),0.001);
    }

    @Test
    public void testBuggyMethod2(){
        assertEquals(-1, myBuggyClass.buggyMethod3(2,-3),0.001);
    }
}

package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by unazi on 11/2/16.
 */
public class BuggyClassTestSC2 {

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
    public void testBuggyMethod2(){
        assertEquals(6, myBuggyClass.buggyMethod2(1,5),0.001);
    }

}

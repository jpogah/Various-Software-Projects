package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by unazi on 11/2/16.
 */
public class BuggyClassTestBC2 {
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
    public void testBuggyMethod2(){
        int result= myBuggyClass.buggyMethod2(0,5);
    }

}

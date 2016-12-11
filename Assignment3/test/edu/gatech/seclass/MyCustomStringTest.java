package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.After;
import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class MyCustomStringTest {

    private MyCustomStringInterface mycustomstring;

    @Before
    public void setUp() {
        mycustomstring = new MyCustomString();
    }

    @After
    public void tearDown() {
        mycustomstring = null;
    }

    @Test
    public void testCountNumbers1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals(7, mycustomstring.countNumbers());
    }
/*
This test checks for null pointer exception in case where the input string is null
 */
    @Test(expected = NullPointerException.class)
    public void testCountNumbers2() {
        mycustomstring.setString(null);
        int result = mycustomstring.countNumbers();
    }

    /*
    This Test checks for cases where the input string empty
     */

    @Test
    public void testCountNumbers3() {
        mycustomstring.setString("");
        assertEquals("0", String.valueOf(mycustomstring.countNumbers()));
    }

    /*
    This Test checks cases where the numbers are at the end of the String
     */

    @Test
    public void testCountNumbers4() {

        mycustomstring.setString("I am 10 years old today 23rd August 2016");
        assertEquals(3,mycustomstring.countNumbers());
    }

    /*
    This Test a case where the string contains only digits
     */
    @Test
    public void testCountNumbers5() {

        mycustomstring.setString("10 3000 40 45 67 11111");
        assertEquals(6,mycustomstring.countNumbers());
    }
/*
This test checks for cases where the string contains only letters
 */
    @Test
    public void testCountNumbers6() {

        mycustomstring.setString("I am testing my string without numbers");
        assertEquals(0,mycustomstring.countNumbers());
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals("d33p md1  i51,it", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, false));
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd2() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals("'bt t0 6snh r6rh", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, true));
    }

    /*
    Test to check for Illegal argument Exception in case where
     starting position is less than or equals 0 and StartFromEnd flag is True;
     */

    @Test(expected = IllegalArgumentException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd3() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals("Starting position must be greater than zero",
                mycustomstring.getEveryNthCharacterFromBeginningOrEnd(0, true));
    }

/*
    Test to check for Illegal argument Exception in case where
     starting position is less than or equals 0 and StartFromEnd flag is False;
     */

    @Test(expected = IllegalArgumentException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd4() {

        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals("Starting position must be greater than zero",
                mycustomstring.getEveryNthCharacterFromBeginningOrEnd(0, false));
    }


    /*
    Below Test checks if the method suitably throws a null pointer exception
         when the input string is null and StartFromEnd flag is false
    */

    @Test(expected = NullPointerException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd5() {
        mycustomstring.setString(null);
        assertEquals("String cannot be null",mycustomstring.getEveryNthCharacterFromBeginningOrEnd(2,false));
    }

    /*
    Below Test checks if the method suitably throws a null pointer exception
         when the input string is null and StartFromEnd flag is True
    */

    @Test(expected = NullPointerException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd6() {
        mycustomstring.setString(null);
        assertEquals("String cannot be null",mycustomstring.getEveryNthCharacterFromBeginningOrEnd(2,true));
    }
    /*
    Below test check for scenario where the Start position exceed the number
    of Character in the string and startFromEnd flag is false;
     */
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd7() {
        mycustomstring.setString("a");
        assertEquals("",mycustomstring.getEveryNthCharacterFromBeginningOrEnd(2,false));
    }

    /*
    Below test check for scenario where the Start position exceed the number
    of Character in the string and startFromEnd flag is True;
     */
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd8() {
        mycustomstring.setString("a");
        assertEquals("",mycustomstring.getEveryNthCharacterFromBeginningOrEnd(2,true));
    }

    /*
    Below test checks if the case of the character is maintained in the return string as in
    the original string and when startFromEnd flag is False
     */

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd9() {

        mycustomstring.setString("This TEst for CAS3");
        assertEquals("hsTs o A3",mycustomstring.getEveryNthCharacterFromBeginningOrEnd(2,false));
    }

   /*
    Below test checks if the case of the character is maintained in the return string as in
    the original string and when startFromEnd flag is true
     */

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd10() {

        mycustomstring.setString("This TEst for CAS3");
        assertEquals("Ti EtfrCS",mycustomstring.getEveryNthCharacterFromBeginningOrEnd(2,true));
    }


    /*
    Below checks the case where the starting position is 1 , it should return the original string
    and startFromEndFlag is false;
     */

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd11() {

        mycustomstring.setString("This TEst for CAS3");
        assertEquals("This TEst for CAS3",mycustomstring.getEveryNthCharacterFromBeginningOrEnd(1,false));
    }


    /*
    Below checks the case where the starting position is 1 , it should return the original string
    and startFromEndFlag is true;
     */

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd12() {

        mycustomstring.setString("This TEst for CAS3");
        assertEquals("This TEst for CAS3",mycustomstring.getEveryNthCharacterFromBeginningOrEnd(1,true));
    }

    /*
    Below checks if negative input as starting position is effectively handled via IllegalArgumentException
    and when startFromEnd flag is true
     */

    @Test(expected = IllegalArgumentException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd13() {

        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals("Starting position must be greater than zero",
                mycustomstring.getEveryNthCharacterFromBeginningOrEnd(-1, true));
    }

    /*
    Below checks if negative input as starting position is effectively handled via IllegalArgumentException
    and when startFromEnd flag is false
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd14() {

        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals("Starting position must be greater than zero",
                mycustomstring.getEveryNthCharacterFromBeginningOrEnd(-1, true));
    }

    @Test
    public void testConvertDigitsToNamesInSubstring1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(17, 23);
        assertEquals("I'd b3tt3r put sZerome dOneSix1ts in this 5tr1n6, right?", mycustomstring.getString());
    }
/*
Below test checks if illegalArgurment exception is suitably handled in case where
starting position is greater than ending position
 */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertDigitsToNamesInSubstring2() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(23, 17);

    }
/*
Below test for scenario where starting position is less than 1 to see if IndexOutOfBound exception is handled
correctly
 */
    @Test(expected = MyIndexOutOfBoundsException.class)
    public void testConvertDigitsToNamesInSubstring3() {

        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(0, 17);

    }

    /*
Below test for scenario where ending position is greater than the
 length of the string to see if IndexOutOfBound exception is handled
correctly
 */

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void testConvertDigitsToNamesInSubstring4() {

        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(2, mycustomstring.getString().length() + 1);

    }

    /*
    Below test if null pointer exception is suitably handled in case where the input string is null
     */

    @Test(expected = NullPointerException.class)
    public void testConvertDigitsToNamesInSubstring5() {

        mycustomstring.setString(null);
        mycustomstring.convertDigitsToNamesInSubstring(2, 5);
    }
/*
Below test check the case where the string contains only digit character
 */
    @Test
    public void testConvertDigitsToNamesInSubstring6() {

        mycustomstring.setString("09 05 2016");
        mycustomstring.convertDigitsToNamesInSubstring(1,
                mycustomstring.getString().length());
        assertEquals("ZeroNine ZeroFive TwoZeroOneSix",mycustomstring.getString());
    }
    /*
    Below test the case where the starting position is the beginning of the string and its
    equals to the ending position.
     */

    @Test
    public void testConvertDigitsToNamesInSubstring7() {

        mycustomstring.setString("1moreday b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(1,1);
        assertEquals("Onemoreday b3tt3r put s0me d161ts in this 5tr1n6, right?", mycustomstring.getString());

    }
/*
    Below test the case where the starting position is the ending of the string and its
    equals to the ending position.
     */


    @Test
    public void testConvertDigitsToNamesInSubstring8() {

        mycustomstring.setString("1moreday b3tt3r put s0me d161ts in this 5tr1n6, right?1");
        int length = mycustomstring.getString().length();
        mycustomstring.convertDigitsToNamesInSubstring(length,length);
        assertEquals("1moreday b3tt3r put s0me d161ts in this 5tr1n6, right?One", mycustomstring.getString());

    }

}

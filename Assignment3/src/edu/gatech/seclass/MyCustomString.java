package edu.gatech.seclass;
import java.util.*;

/**
 * Created by unazi on 9/4/16.
 */
public class MyCustomString implements MyCustomStringInterface {

    private StringBuilder myStr = null ;

    @Override
    public String getString() {
        return myStr.toString();
    }

    @Override
    public void setString(String string) {
        this.myStr = new StringBuilder(string);

    }


    @Override
    public int countNumbers() {
        if (myStr == null) {
            throw new NullPointerException("String is null");
        }
        String temp = myStr.toString();

        int count = 0;
        boolean num = false;
        for(int i = 0; i < temp.length(); i++){
            if(Character.isDigit(temp.charAt(i)) & !num){
                num = true;

            }
            if ((Character.isWhitespace(temp.charAt(i)) & num ) ||
                    (Character.isLetter(temp.charAt(i)) & num )){
                count++;
                num = false ;
            }
            if( i == temp.length() - 1 & num){
                count++;
            }
        }

        return count;
    }

    @Override
    public String getEveryNthCharacterFromBeginningOrEnd(int n, boolean startFromEnd) {
        String temp ;
        StringBuilder result = new StringBuilder();
        if( n <= 0){
            throw new IllegalArgumentException("Starting position must be greater than zero");
        }
        if (myStr == null){
            throw new NullPointerException("String cannot be null");
        }
        if (startFromEnd){
            temp = new StringBuilder(myStr.toString()).reverse().toString();
        }
        else {
            temp = myStr.toString();
        }

        int i = 1;
        int j = n ;
        while (j < temp.length() + 1){
            result.append(temp.charAt(j-1));
            i++;
            j = i * n;
        }

        if (startFromEnd) {
            return result.reverse().toString();
        }
        else {
            return result.toString();
        }
    }

    @Override
    public void convertDigitsToNamesInSubstring(int startPosition, int endPosition) {

        if(startPosition <= endPosition & startPosition > 0 & endPosition > 0 & myStr==null){
            throw new NullPointerException("String cannot be null");
        }
        if (startPosition > endPosition){
            throw new IllegalArgumentException("Start position cannot be greater than end position");
        }

        if ( startPosition  < 1  || endPosition > myStr.length()){
            throw new MyIndexOutOfBoundsException("Index out of bound");
        }

        //In java string are index from 0
        startPosition--;
        endPosition--;
        HashMap<Integer,String>  hashMap = new HashMap<Integer,String>();
        hashMap.put(1,"One");
        hashMap.put(2,"Two");
        hashMap.put(3, "Three");
        hashMap.put(4, "Four");
        hashMap.put(5, "Five");
        hashMap.put(6, "Six");
        hashMap.put(7, "Seven");
        hashMap.put(8, "Eight");
        hashMap.put(9, "Nine");
        hashMap.put(0, "Zero");


        StringBuilder result = new StringBuilder();

        for(int i = 0 ; i < startPosition ; i++){
            result.append(myStr.charAt(i));
        }
        for(int i = startPosition; i <= endPosition; i++){
            if(Character.isDigit(myStr.charAt(i))){
                result.append(hashMap.get(Character.getNumericValue(myStr.charAt(i))));
            }
            else{
                result.append(myStr.charAt(i));
            }
        }

        for(int i = endPosition+1; i < myStr.length(); i++){
            result.append(myStr.charAt(i));
        }

        myStr = new StringBuilder(result.toString());


    }

}

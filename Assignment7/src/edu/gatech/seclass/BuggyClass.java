package edu.gatech.seclass;

/**
 * Created by unazi on 11/1/16.
 */
public class BuggyClass {

    public int buggyMethod1(int x, int y) {
        if (y > 0) {
            y = y / x;
        } else {
            y = y + 2;
            x = x * 2;
            System.out.println(x);
            System.out.println(y);
        }
        return y;

    }

    public int buggyMethod2(int a , int b) {
        int result1 = 0;
        if (b > 0) {
            result1 = b/a ;
            if(result1 > 0 ){
                result1 = result1 + 1;
            }
            }
            return result1;
    }


    public int buggyMethod3(int a, int b) {
        if (b > 0) {
            b = b / a;
        } else {
            b= b+ 2;
        }
        return b;

    }


    public int buggyMethod4(int a, int b) {
        if (b > 0) {
            b = b / a;
        } else {
            b= 3 * a;
        }
        return b;

    }

    public void buggyMethod5 (int i) {
       /*
       It is not possible to create a test with 100% statement coverage because  such a test
       will certainly execute the line 4 of the code thus revealing the fault.The denominator will
       always be zero irrespective of the value of parameter i or any other parameter.
        */
    }



    }

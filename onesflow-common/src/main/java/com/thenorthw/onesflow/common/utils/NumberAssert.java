package com.thenorthw.onesflow.common.utils;

/**
 * Created by theNorthW on 17/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public class NumberAssert {

    public static boolean isLong(String s){
        try {
            Long.parseLong(s);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}

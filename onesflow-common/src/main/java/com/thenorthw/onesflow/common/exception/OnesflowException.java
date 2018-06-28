package com.thenorthw.onesflow.common.exception;

/**
 * Created by theNorthW on 12/07/2017.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public class OnesflowException extends RuntimeException{
    public OnesflowError error;

    public OnesflowException(OnesflowError error){
        this.error = error;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"code\":\"");
        sb.append(error.getCode());
        sb.append("\",");

        sb.append("\"msg\":\"");
        sb.append(error.getMessage());
        sb.append("\",");

        if(error.getSolution() == null || error.getSolution().isEmpty()) {
            sb.append("\"solution\":\"");
            sb.append(error.getSolution());
            sb.append("\"");
        }

        return sb.toString();
        
        


    }
}

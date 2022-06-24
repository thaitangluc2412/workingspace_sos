package com.cnpm.workingspace.exception;

public class DayMonthIntervalException extends RuntimeException{
    public DayMonthIntervalException(){
        super("Day Month Interval Exception");
    }
    public DayMonthIntervalException(String message){
        super(message);
    }
}

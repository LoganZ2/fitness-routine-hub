package com.MIE350.FitnessRoutineHub.controller.exceptions;

public class MissingDayInfoException extends RuntimeException {
    public MissingDayInfoException() {
        super("Cannot find the day info for this date");
    }
}

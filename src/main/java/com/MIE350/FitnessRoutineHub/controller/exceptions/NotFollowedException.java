package com.MIE350.FitnessRoutineHub.controller.exceptions;

public class NotFollowedException extends RuntimeException {
    public NotFollowedException() {
        super("Not followed");
    }
}

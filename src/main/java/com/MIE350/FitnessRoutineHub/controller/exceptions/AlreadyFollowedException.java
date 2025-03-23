package com.MIE350.FitnessRoutineHub.controller.exceptions;

public class AlreadyFollowedException extends RuntimeException {
    public AlreadyFollowedException() {
        super("Already followed");
    }
}

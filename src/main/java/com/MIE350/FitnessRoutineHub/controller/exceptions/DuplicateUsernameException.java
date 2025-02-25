package com.MIE350.FitnessRoutineHub.controller.exceptions;

public class DuplicateUsernameException extends RuntimeException{
    public DuplicateUsernameException() {
        super("Username already exists");
    }

}

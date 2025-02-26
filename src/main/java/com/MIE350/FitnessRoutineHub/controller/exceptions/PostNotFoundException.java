package com.MIE350.FitnessRoutineHub.controller.exceptions;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException() {
        super("Post not found");
    }

}

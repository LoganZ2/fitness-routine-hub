package com.MIE350.FitnessRoutineHub.controller.exceptions;

import java.util.Arrays;

public class MissingRequiredValuesException extends RuntimeException {
    public MissingRequiredValuesException(String... values) {
        super("Missing values: " + Arrays.toString(values));
    }
}


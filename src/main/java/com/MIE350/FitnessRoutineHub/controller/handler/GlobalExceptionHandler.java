package com.MIE350.FitnessRoutineHub.controller.handler;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorRes handleCustomException(RuntimeException ex) {
        return new ErrorRes(500, ex.getMessage());
    }
}
@Data
class ErrorRes{
    private int code;
    public String message;
    public ErrorRes(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

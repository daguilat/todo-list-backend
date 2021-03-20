package com.example.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class Exception extends RuntimeException{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Exception(String message){
        super(message);
    }
}

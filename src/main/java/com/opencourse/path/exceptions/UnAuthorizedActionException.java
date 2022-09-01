package com.opencourse.path.exceptions;

public class UnAuthorizedActionException extends RuntimeException{
    public UnAuthorizedActionException(){
        super("unauthorized action");
    }    
}

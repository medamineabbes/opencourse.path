package com.opencourse.path.exceptions;

public class CustomAuthenticationException extends RuntimeException{
    
    public CustomAuthenticationException(){
        super("authentication error");
    }
}

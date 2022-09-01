package com.opencourse.path.exceptions;


public class ElementNotFoundException extends RuntimeException{
    public ElementNotFoundException(String id){
        super("element with id : " + id + " not found");
    }    
}

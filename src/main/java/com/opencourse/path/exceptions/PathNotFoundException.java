package com.opencourse.path.exceptions;

public class PathNotFoundException extends RuntimeException{
    public PathNotFoundException(String id){
        super("path with id : " + id + " not found");
    }
}

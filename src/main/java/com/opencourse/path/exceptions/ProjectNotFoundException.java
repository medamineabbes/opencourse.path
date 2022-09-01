package com.opencourse.path.exceptions;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(String id){
        super("project with id : " + id + " not found");
    }
}

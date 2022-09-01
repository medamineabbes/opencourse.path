package com.opencourse.path.exceptions;

public class TopicNotFoundException extends RuntimeException{
    public TopicNotFoundException(String id){
        super("Topic with id : " + id + " not found");
    }    
}

package com.opencourse.path.exceptions;

public class UserNotSubscribedException extends RuntimeException{
    public UserNotSubscribedException(Long userId,String pathId){
        super("user with id : " + userId + " not subscribed to path with id : " + pathId);
    }
}

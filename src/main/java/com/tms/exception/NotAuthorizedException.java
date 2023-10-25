package com.tms.exception;

public class NotAuthorizedException extends IllegalAccessException{

    public NotAuthorizedException (String msg){
        super(msg);
    }
}

package com.tms.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super("Object not found");
    }
}

package com.epam.esm.exception;

public class AlreadyExistElementException extends Exception {

    public AlreadyExistElementException() {
        super();
    }

    public String getMessage() {
        return "Such element is already exist";
    }
}

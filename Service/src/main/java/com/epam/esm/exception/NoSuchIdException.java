package com.epam.esm.exception;

public class NoSuchIdException extends Exception {

    public NoSuchIdException() {
        super();
    }

    public String getMessage() {
        return "There is no element with such id";
    }
}

package com.epam.esm.exception;

public class EmptyListException extends Exception{

    public EmptyListException() {
        super();
    }

    public String getMessage(){
        return "Empty list";
    }
}

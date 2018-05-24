package com.beeva.exceptions;

public class PersonNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public PersonNotFoundException(String error) {
        super(error);
    }

}

package com.tdevred.rentals.authentication.exceptions;

public class BadLoginException extends Exception {
    public BadLoginException(String msg) {
        super(msg);
    }

    public BadLoginException() {
        super();
    }
}

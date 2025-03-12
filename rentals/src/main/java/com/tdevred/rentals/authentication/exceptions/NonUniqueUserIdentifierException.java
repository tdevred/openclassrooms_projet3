package com.tdevred.rentals.authentication.exceptions;

public class NonUniqueUserIdentifierException extends Exception {
    public NonUniqueUserIdentifierException(String msg) {
        super(msg);
    }
}

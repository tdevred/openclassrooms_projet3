package com.tdevred.rentals.authentication;

import com.tdevred.rentals.authentication.exceptions.BadLoginException;
import com.tdevred.rentals.authentication.exceptions.NonUniqueUserIdentifierException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthenticationExceptionHandler {
    @ExceptionHandler(NonUniqueUserIdentifierException.class)
    public ResponseEntity<?> handleNonUniqueUserIdentifier(NonUniqueUserIdentifierException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(BadLoginException.class)
    public ResponseEntity<?> handleBadLogin(BadLoginException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

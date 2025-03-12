package com.tdevred.rentals.presentation;

import com.tdevred.rentals.services.exceptions.NotAuthorizedToModifyRentalException;
import com.tdevred.rentals.services.exceptions.UnknownRentalException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotAuthorizedToModifyRentalException.class)
    public ResponseEntity<String> handleConversion(NotAuthorizedToModifyRentalException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnknownRentalException.class)
    public ResponseEntity<String> handleUnknown(UnknownRentalException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}

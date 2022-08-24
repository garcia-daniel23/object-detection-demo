package com.danielagarcia.objectdetectiondemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Json body must contain either \"imageUrl\" or \"imageData\" " +
        " but not both")
public class ImageURLAndDataException extends RuntimeException {
    public ImageURLAndDataException(String errorMessage) {
        super(errorMessage);
    }
}

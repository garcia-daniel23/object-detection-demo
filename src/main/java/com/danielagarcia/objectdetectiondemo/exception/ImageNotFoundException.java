package com.danielagarcia.objectdetectiondemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Image not found")
public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

package com.danielagarcia.objectdetectiondemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid input for Image")
public class ImageNotBase64StringException extends RuntimeException {
    public ImageNotBase64StringException(String errorMessage) {
        super(errorMessage);
    }
}

package com.danielagarcia.objectdetectiondemo.model;

import com.danielagarcia.objectdetectiondemo.exception.ImageNotBase64StringException;
import com.danielagarcia.objectdetectiondemo.exception.ImageURLAndDataException;
import com.danielagarcia.objectdetectiondemo.validator.StringValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class Image {
    private Integer imageId;
    private String label;
    private String imageData;
    private String imageURL;
    private List<ImageObject> imageObjects;
    private boolean enable;


    public void hasErrors() {
        if (!StringValidator.isBase64(this.imageData)) {
            throw new ImageNotBase64StringException("imageData does not contain a Base64 encoded String");
        }
        if(this.imageData != null && !this.imageData.trim().isEmpty() && this.imageURL != null && !this.imageURL.trim().isEmpty()) {
            throw new ImageURLAndDataException("imageData and imageURL can't both be present");
        }

    }
}

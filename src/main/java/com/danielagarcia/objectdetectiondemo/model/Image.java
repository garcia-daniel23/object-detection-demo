package com.danielagarcia.objectdetectiondemo.model;

import com.danielagarcia.objectdetectiondemo.exception.ImageNotBase64StringException;
import com.danielagarcia.objectdetectiondemo.exception.ImageURLAndDataException;
import com.danielagarcia.objectdetectiondemo.validator.StringValidator;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Image {
    private Integer imageId;
    private String label;
    private String imageData;
    private String imageURL;
    private List<ImageObject> imageObjects;
    private Boolean enable;

    public boolean isEnable() {
        return this.enable.booleanValue();
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void hasErrors() {
        if (this.imageData != null && !StringValidator.isBase64(this.imageData))
            throw new ImageNotBase64StringException("imageData does not contain a Base64 encoded String");
        if ((this.imageData == null || this.imageData.trim().isEmpty()) && (this.imageURL == null || this.imageURL.trim().isEmpty()))
            throw new ImageURLAndDataException("imageData and imageUrl can't both be null");
    }
}

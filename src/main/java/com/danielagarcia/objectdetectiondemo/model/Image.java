package com.danielagarcia.objectdetectiondemo.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class Image {
    private String imageId;
    private String imageData;
    private List<ImageObject> imageObjects;
}

package com.danielagarcia.objectdetectiondemo.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class ImageObject {
    private Integer objectId;
    private String name;
    private Float score;
    private List<Image> images;
}

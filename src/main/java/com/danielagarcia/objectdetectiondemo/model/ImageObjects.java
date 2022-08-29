package com.danielagarcia.objectdetectiondemo.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class ImageObjects {
    private Integer imageId;
    private Integer objectId;
    private Float score;
}

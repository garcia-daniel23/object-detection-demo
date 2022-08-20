package com.danielagarcia.objectdetectiondemo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ImageObject {
    private Integer objectId;
    private String name;
}

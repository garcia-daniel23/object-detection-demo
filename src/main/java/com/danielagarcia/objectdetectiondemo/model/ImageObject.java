package com.danielagarcia.objectdetectiondemo.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class ImageObject {
    private Integer objectId;
    private String name;
}

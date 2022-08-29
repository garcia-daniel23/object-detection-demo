package com.danielagarcia.objectdetectiondemo.service;

import com.danielagarcia.objectdetectiondemo.model.ImageObject;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoogleVisionServiceImpl {

    @Autowired
    private CloudVisionTemplate cloudVisionTemplate;

    public List<ImageObject > detectImageObjects(Resource image) throws HttpMediaTypeNotSupportedException {
        if (image == null)
            throw new RuntimeException("Image Resource is null");
        // [START spring_vision_image_labelling]
        AnnotateImageResponse response =
                this.cloudVisionTemplate.analyzeImage(
                        image, Feature.Type.LABEL_DETECTION);

        if (response.hasError()) {
            throw new HttpMediaTypeNotSupportedException("Google Vision API Error: " + response.getError());
        }

        Map<String, Float> imageLabels =
                response.getLabelAnnotationsList().stream()
                        .collect(
                                Collectors.toMap(
                                        EntityAnnotation::getDescription,
                                        EntityAnnotation::getScore,
                                        (u, v) -> {
                                            throw new IllegalStateException(String.format("Duplicate key %s", u));
                                        },
                                        LinkedHashMap::new));
        // [END spring_vision_image_labelling]

        List<ImageObject> imageObjectList = new ArrayList();
        imageLabels.keySet().forEach(label -> imageObjectList.add(ImageObject.builder()
                        .name(label)
                .build()));

        return imageObjectList;
    }
}

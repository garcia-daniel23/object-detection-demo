package com.danielagarcia.objectdetectiondemo.service;

import com.danielagarcia.objectdetectiondemo.model.ImageObject;
import com.danielagarcia.objectdetectiondemo.repository.ImagesRepositoryImpl;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private CloudVisionTemplate cloudVisionTemplate;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ImagesRepositoryImpl imagesRepository;


    public ImageObject detectImageObjects(String imageUrl) {
        // [START spring_vision_image_labelling]
        AnnotateImageResponse response =
                this.cloudVisionTemplate.analyzeImage(
                        this.resourceLoader.getResource("classpath:" + imageUrl), Type.LABEL_DETECTION);

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


        return ImageObject.builder()
                .objectId(1)
                .build();
    }

    @Override
    public ImageObject detectImageObjects() {
        return null;
    }
}

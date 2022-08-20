package com.danielagarcia.objectdetectiondemo.controller;


import com.danielagarcia.objectdetectiondemo.model.Image;
import com.danielagarcia.objectdetectiondemo.service.ImagesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ImagesController {

    private static final String BASE_MAPPING = "/images";
    private static final String IMAGE_ID_MAPPING = "/{imageId}";

    @Autowired
    ImagesServiceImpl imagesService;

    @GetMapping(BASE_MAPPING)
    public ArrayList<Image> getImages(@RequestParam(required = false) List<String> objects) {

        return new ArrayList<Image>(Arrays.asList(new Image[]{new Image()}));
    }

    @GetMapping(BASE_MAPPING + IMAGE_ID_MAPPING)
    public String getImageByID(@PathVariable String imageId) {

        imagesService.detectImageObjects(imageId);
        return "This is the imageId: " + imageId;
    }

    @PostMapping(BASE_MAPPING)
    public Image uploadImage() {
        return null;
    }

}

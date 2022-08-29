package com.danielagarcia.objectdetectiondemo.controller;


import com.danielagarcia.objectdetectiondemo.exception.ImageNotFoundException;
import com.danielagarcia.objectdetectiondemo.exception.ImageURLAndDataException;
import com.danielagarcia.objectdetectiondemo.model.Image;
import com.danielagarcia.objectdetectiondemo.service.ImagesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ImagesController {

    private static final String BASE_MAPPING = "/images";
    private static final String IMAGE_ID_MAPPING = "/{imageId}";

    @Autowired
    ImagesServiceImpl imagesService;

    @GetMapping(BASE_MAPPING)
    public ResponseEntity<ArrayList<Image>> getImages(@RequestParam(required = false) List<String> objects) {

        List<Image> allImages = imagesService.getImages(objects);

        return new ResponseEntity(allImages, HttpStatus.OK);
    }

    @GetMapping(BASE_MAPPING + IMAGE_ID_MAPPING)
    public ResponseEntity<Image> getImageById(@PathVariable Integer imageId) {

        Image image = imagesService.getImageById(imageId);
        if (image == null)
            throw new ImageNotFoundException("Image with that id=" + imageId);

        return new ResponseEntity(image, HttpStatus.OK);
    }

    @PostMapping(BASE_MAPPING)
    public ResponseEntity<Image> uploadImage(@RequestBody Image imageToUpload) throws HttpMediaTypeNotSupportedException {
        if (imageToUpload.getImageData() != null && !imageToUpload.getImageData().trim().isEmpty() &&
                imageToUpload.getImageURL() != null && !imageToUpload.getImageURL().trim().isEmpty()) {
            throw new ImageURLAndDataException("imageData and imageURL can't both be present");
        }
        if (imageToUpload.getEnable() == null)
            imageToUpload.setEnable(true);
        imageToUpload.hasErrors();
        Image uploadedImage = imagesService.uploadImage(imageToUpload);

        return new ResponseEntity(uploadedImage, HttpStatus.OK);
    }

}

package com.danielagarcia.objectdetectiondemo.service;

import com.danielagarcia.objectdetectiondemo.model.Image;
import com.danielagarcia.objectdetectiondemo.model.ImageObject;

import java.util.List;

public interface ImagesService {

    public Image uploadImage(Image imageToUpload);

    public Image getImageById(Integer imageId);

    public Image getImagesContainingObjects(List<ImageObject> objects);

    public List<Image> getImages(List<String> objects);
}

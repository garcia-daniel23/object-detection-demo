package com.danielagarcia.objectdetectiondemo.repository;

import com.danielagarcia.objectdetectiondemo.model.Image;
import com.danielagarcia.objectdetectiondemo.model.ImageObject;

import java.util.List;

public interface ImagesRepository {

    public List<Image> getImages();

    public List<Image> getImagesWithObject(List<ImageObject> objects);

    public Image getImageById();


    public Image insertImage(Image image);
}

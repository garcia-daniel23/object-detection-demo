package com.danielagarcia.objectdetectiondemo.repository;

import com.danielagarcia.objectdetectiondemo.model.Image;
import com.danielagarcia.objectdetectiondemo.model.ImageObject;

import java.util.List;

public interface ImageRepository {

    public List<Image> getImages();

    public List<Image> getImageById(List<Integer> imageIds);

    public Integer insertImage(Image image);

}

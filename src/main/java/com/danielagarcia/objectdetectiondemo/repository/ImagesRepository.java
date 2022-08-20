package com.danielagarcia.objectdetectiondemo.repository;

import com.danielagarcia.objectdetectiondemo.model.Image;

import java.util.List;

public interface ImagesRepository {

    public List<Image> getImages();
}

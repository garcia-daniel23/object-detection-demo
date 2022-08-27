package com.danielagarcia.objectdetectiondemo.repository;

import com.danielagarcia.objectdetectiondemo.model.ImageObjects;

import java.util.List;

public interface ImageObjectsRepository {

    public List<ImageObjects> getImageObjectsByImageId(Integer imageId);

    public List<ImageObjects> getImageObjectsByObjectId(Integer imageId);

    public void insertImageObjects(List<ImageObjects> imageObjects);
}

package com.danielagarcia.objectdetectiondemo.repository;

import com.danielagarcia.objectdetectiondemo.model.ImageObjects;

import java.util.List;

public interface ImageObjectsRepository {

    public List<ImageObjects> getImageObjectsByImageId(List<Integer> imageId);

    public List<ImageObjects> getImageObjectsByObjectId(List<Integer> objectId);

    public int[] insertImageObjects(List<ImageObjects> imageObjects);
}

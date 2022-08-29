package com.danielagarcia.objectdetectiondemo.repository;

import com.danielagarcia.objectdetectiondemo.model.ImageObject;

import java.util.List;

public interface ImageObjectRepository {

    public List<ImageObject> getImageObjectByObjectId(List<Integer> objectId);

    public List<ImageObject> getImageObjectByJoiningImageId(List<Integer> imageId);

    public List<ImageObject> getImageObjectByName(List<String> name);

    public int[] insertImageObject(List<ImageObject> imageObject);

    }

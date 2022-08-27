package com.danielagarcia.objectdetectiondemo.repository;

import com.danielagarcia.objectdetectiondemo.model.ImageObject;

import java.util.List;

public interface ImageObjectRepository {

    public List<ImageObject> getImageObjectByObjectId(Integer objectId);
    public List<ImageObject> getImageObjectByName(String name);

    }

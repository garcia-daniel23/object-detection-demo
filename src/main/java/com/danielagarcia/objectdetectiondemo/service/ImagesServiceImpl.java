package com.danielagarcia.objectdetectiondemo.service;

import com.danielagarcia.objectdetectiondemo.model.Image;
import com.danielagarcia.objectdetectiondemo.model.ImageObject;
import com.danielagarcia.objectdetectiondemo.repository.ImagesRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private ImagesRepositoryImpl imagesRepository;

    @Autowired
    private GoogleVisionServiceImpl googleVisionService;


    @Override
    public Image uploadImage(Image imageToUpload) {
        return null;
    }

    @Override
    public Image getImageById(Integer imageId) {
        return null;
    }

    @Override
    public Image getImagesContainingObjects(List<ImageObject> objects) {
        return null;
    }

    @Override
    public List<Image> getImages() {
        return null;
    }
}

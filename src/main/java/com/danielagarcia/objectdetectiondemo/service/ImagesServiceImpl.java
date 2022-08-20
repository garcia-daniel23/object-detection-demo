package com.danielagarcia.objectdetectiondemo.service;

import com.danielagarcia.objectdetectiondemo.model.ImageObject;
import com.danielagarcia.objectdetectiondemo.repository.ImagesRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    ImagesRepositoryImpl imagesRepository;

    @Override
    public ImageObject detectImageObjects() {
        return null;
    }
}

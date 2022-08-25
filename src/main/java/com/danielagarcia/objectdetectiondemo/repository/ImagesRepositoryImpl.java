package com.danielagarcia.objectdetectiondemo.repository;

import com.danielagarcia.objectdetectiondemo.model.Image;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImagesRepositoryImpl implements ImagesRepository {


    @Override
    public List<Image> getImages() {
        return null;
    }
}

package com.danielagarcia.objectdetectiondemo.repository;

import com.danielagarcia.objectdetectiondemo.model.Image;
import com.danielagarcia.objectdetectiondemo.model.ImageObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImagesRepositoryImpl implements ImagesRepository {

    @Query()
    @Override
    public List<Image> getImages() {
        return null;
    }

    @Override
    public List<Image> getImagesWithObject(List<ImageObject> objects) {
        return null;
    }

    @Override
    public Image getImageById() {
        return null;
    }

    @Override
    public Image insertImage(Image image) {
        return null;
    }
}

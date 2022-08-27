package com.danielagarcia.objectdetectiondemo.service;

import com.danielagarcia.objectdetectiondemo.model.Image;
import com.danielagarcia.objectdetectiondemo.model.ImageObject;
import com.danielagarcia.objectdetectiondemo.repository.ImageObjectRepositoryImpl;
import com.danielagarcia.objectdetectiondemo.repository.ImageRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private ImageRepositoryImpl imageRepository;
    @Autowired
    private ImageObjectRepositoryImpl imageObjectRepository;
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
    public List<Image> getImages(List<String> objects) {
        List<Image> images;
        if (objects != null && !objects.isEmpty())
            images = getImagesByObjects(objects);
        else
            images = getAllImages();

        return images;
    }

    private List<Image> getAllImages() {
        List<Image> images = null;

        images = imageRepository.getImages();

        return images;
    }

    private List<Image> getImagesByObjects(List<String> objects) {
        List<Image> images = null;
        List<ImageObject> imageObjectList;


        List<Integer> imageIds = images.stream()
                .map(image -> image.getImageId())
                .collect(Collectors.toList());
        return null;
    }

    private List<Image> attachObjectsToImages(List<Image> images, List<ImageObject> imageObjectList) {

        return null;
    }

}

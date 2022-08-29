package com.danielagarcia.objectdetectiondemo.service;

import com.danielagarcia.objectdetectiondemo.model.Image;
import org.springframework.web.HttpMediaTypeNotSupportedException;

import java.net.URISyntaxException;
import java.util.List;

public interface ImagesService {

    public Image uploadImage(Image imageToUpload) throws URISyntaxException, HttpMediaTypeNotSupportedException;

    public Image getImageById(Integer imageId);

    public List<Image> getImages(List<String> objects);
}

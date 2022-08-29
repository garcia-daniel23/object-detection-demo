package com.danielagarcia.objectdetectiondemo.service;

import com.danielagarcia.objectdetectiondemo.model.Image;
import com.danielagarcia.objectdetectiondemo.model.ImageObject;
import com.danielagarcia.objectdetectiondemo.model.ImageObjects;
import com.danielagarcia.objectdetectiondemo.repository.ImageObjectRepositoryImpl;
import com.danielagarcia.objectdetectiondemo.repository.ImageObjectsRepositoryImpl;
import com.danielagarcia.objectdetectiondemo.repository.ImageRepositoryImpl;
import com.danielagarcia.objectdetectiondemo.utility.ImageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotSupportedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ImagesServiceImpl implements ImagesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesServiceImpl.class);

    @Autowired
    private ImageRepositoryImpl imageRepository;
    @Autowired
    private ImageObjectRepositoryImpl imageObjectRepository;
    @Autowired
    private ImageObjectsRepositoryImpl imageObjectsRepository;
    @Autowired
    private GoogleVisionServiceImpl googleVisionService;
    @Autowired
    ResourceLoader resourceLoader;

    private static final int FIRST_IN_LIST = 0;
    private static final String NO_LABEL_DEFAULT = "DISABLED_IMAGE";


    @Override
    public Image uploadImage(Image imageToUpload) throws RuntimeException, HttpMediaTypeNotSupportedException {
        Integer imageId;

        if (imageToUpload.getImageURL() != null && !imageToUpload.getImageURL().isEmpty()) {
            String imageData = ImageParser.imageUrlAsBase64Str(imageToUpload.getImageURL());
            imageToUpload.setImageData(imageData);
        }
        Resource imageResource = ImageParser.base64StrAsResource(imageToUpload.getImageData());

        if (imageToUpload.getLabel() == null || imageToUpload.getLabel().trim().isEmpty()) {
            imageToUpload.setLabel(NO_LABEL_DEFAULT);
        }

        if (!imageToUpload.isEnable()) {
            imageId = imageRepository.insertImage(imageToUpload);
            return imageRepository.getImageById(Collections.singletonList(imageId)).get(FIRST_IN_LIST);
        }

        List<ImageObject> detectedImageObjectList = googleVisionService.detectImageObjects(imageResource);
        List<String> detectedImageObjectNames = detectedImageObjectList.stream()
                .map(imageObject -> imageObject.getName())
                .collect(Collectors.toList());

        imageObjectRepository.insertImageObject(detectedImageObjectList);
        detectedImageObjectList = imageObjectRepository.getImageObjectByName(detectedImageObjectNames);

        if (!detectedImageObjectNames.isEmpty() && imageToUpload.isEnable() && imageToUpload.getLabel().equals(NO_LABEL_DEFAULT)) {
            StringBuilder createdLabel = new StringBuilder();
            detectedImageObjectNames.forEach(detectedName -> {
                createdLabel.append(detectedName);
            });
            imageToUpload.setLabel(createdLabel.toString());
        }

        imageId = imageRepository.insertImage(imageToUpload);
        //Insert relationships
        List<ImageObjects> imageToObjectList = new ArrayList();
        detectedImageObjectList.forEach(imageObject -> {
            imageToObjectList.add(ImageObjects.builder()
                    .objectId(imageObject.getObjectId())
                    .imageId(imageId)
                    .build());

        });
        imageObjectsRepository.insertImageObjects(imageToObjectList);

        List<Image> image = imageRepository.getImageById(Collections.singletonList(imageId));
        image = attachObjectsToImages(image, detectedImageObjectList, imageToObjectList);

        return image.get(FIRST_IN_LIST);
    }

    @Override
    public Image getImageById(Integer imageId) {
        List<Image> images;
        List<Integer> imageIds = new ArrayList();
        List<ImageObject> imageObjectList;
        List<ImageObjects> imageToObjectList;
        List<Integer> objectIds;

        imageIds.add(imageId);
        images = imageRepository.getImageById(imageIds);
        imageToObjectList = imageObjectsRepository.getImageObjectsByImageId(imageIds);
        objectIds = imageToObjectList.stream()
                .map(imageToObject -> imageToObject.getObjectId())
                .collect(Collectors.toList());
        if (!objectIds.isEmpty()) {
            imageObjectList = imageObjectRepository.getImageObjectByObjectId(objectIds);
            images = attachObjectsToImages(images, imageObjectList, imageToObjectList);
        }

        return images.get(FIRST_IN_LIST);
    }

    @Override
    public List<Image> getImages(List<String> objects) {
        List<Image> images;
        List<ImageObject> imageObjectList;
        List<ImageObjects> imageToObjectList;
        List<Integer> imageIds;
        List<Integer> objectIds;

        if (objects != null && !objects.isEmpty()) {
            imageObjectList = imageObjectRepository.getImageObjectByName(objects);
            objectIds = imageObjectList.stream()
                    .map(imageObject -> imageObject.getObjectId())
                    .collect(Collectors.toList());

            if (objectIds.isEmpty())
                return new ArrayList<Image>();

            imageToObjectList = imageObjectsRepository.getImageObjectsByObjectId(objectIds);
            imageIds = imageToObjectList.stream()
                    .map(imageToObject -> imageToObject.getImageId())
                    .collect(Collectors.toList());

            //Find the rest of the objects in the image
            imageObjectList = imageObjectRepository.getImageObjectByJoiningImageId(imageIds);

            if (imageIds.isEmpty())
                return new ArrayList<Image>();
            images = imageRepository.getImageById(imageIds);
        } else {
            images = imageRepository.getImages();
            imageIds = images.stream()
                    .map(image -> image.getImageId())
                    .collect(Collectors.toList());
            if (imageIds.isEmpty())
                return new ArrayList<Image>();

            imageToObjectList = imageObjectsRepository.getImageObjectsByImageId(imageIds);
            objectIds = imageToObjectList.stream()
                    .map(imageToObject -> imageToObject.getObjectId())
                    .collect(Collectors.toList());
            if (objectIds.isEmpty())
                return new ArrayList<Image>();
            imageObjectList = imageObjectRepository.getImageObjectByObjectId(objectIds);
        }

        images = attachObjectsToImages(images, imageObjectList, imageToObjectList);
        return images;
    }

    private List<Image> attachObjectsToImages(List<Image> images, List<ImageObject> imageObjectList, List<ImageObjects> imageToObjectList) {
        Map<Integer, Image> imageMap = images.stream()
                .collect(Collectors.toMap(Image::getImageId, Function.identity()));
        Map<Integer, ImageObject> imageObjectMap = imageObjectList.stream()
                .collect(Collectors.toMap(ImageObject::getObjectId, Function.identity()));

        imageToObjectList.forEach(imageToObject -> {
            Integer imageId = imageToObject.getImageId();
            Integer objectId = imageToObject.getObjectId();

            try {
                if (imageMap.get(imageId).getImageObjects() == null)
                    imageMap.get(imageId).setImageObjects(new ArrayList());

                imageMap.get(imageId).getImageObjects().add(imageObjectMap.get(objectId));
            } catch (NullPointerException ex) {
                LOGGER.debug("Object or Image may not exist in table", ex);
            }

        });

        return imageMap.values().stream().toList();
    }


}

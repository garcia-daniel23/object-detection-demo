package com.danielagarcia.objectdetectiondemo.repository;

import com.danielagarcia.objectdetectiondemo.model.ImageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImageObjectRepositoryImpl implements ImageObjectRepository {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public ImageObjectRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ImageObject> getImageObjectByObjectId(Integer objectId) {

        return null;
    }

    public List<ImageObject> getImageObjectByName(String name) {

        return null;
    }

    private static final String GET_IMAGE_OBJECT_BY_NAME = "";
    private static final String INSERT_IMAGE_OBJECT = "";

}

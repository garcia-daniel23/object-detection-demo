package com.danielagarcia.objectdetectiondemo.repository;

import com.danielagarcia.objectdetectiondemo.model.ImageObjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ImageObjectsRepositoryImpl implements ImageObjectsRepository {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public ImageObjectsRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<ImageObjects> getImageObjectsByImageId(Integer imageId) {
        return jdbcTemplate.query(GET_IMAGE_OBJECTS_BY_IMAGE_ID, new ImageObjectsMapper());
    }

    @Override
    public List<ImageObjects> getImageObjectsByObjectId(Integer imageId) {
        return jdbcTemplate.query(GET_IMAGE_OBJECTS_BY_OBJECT_ID, new ImageObjectsMapper());
    }

    @Override
    public void insertImageObjects(List<ImageObjects> imageObjectsList) {

    }


    static class ImageObjectsMapper implements RowMapper<ImageObjects> {
        @Override
        public ImageObjects mapRow(ResultSet rs, int rowNum) throws SQLException {

            return ImageObjects.builder()
                    .imageId(rs.getInt("image_id"))
                    .objectId(rs.getInt("object_id"))
                    .build();
        }
    }

    private static final String GET_IMAGE_OBJECTS_BY_IMAGE_ID = "";
    private static final String GET_IMAGE_OBJECTS_BY_OBJECT_ID = "";
    private static final String INSERT_IMAGE_OBJECTS = "insert into image_objects values";
}

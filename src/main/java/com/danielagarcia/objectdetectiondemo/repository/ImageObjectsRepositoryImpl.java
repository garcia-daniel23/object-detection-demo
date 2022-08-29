package com.danielagarcia.objectdetectiondemo.repository;

import com.danielagarcia.objectdetectiondemo.model.ImageObjects;
import com.danielagarcia.objectdetectiondemo.utility.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
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
    public List<ImageObjects> getImageObjectsByImageId(List<Integer> imageId) {
        String sql = SqlBuilder.buildInClause(GET_IMAGE_OBJECTS_BY_IMAGE_ID, imageId);
        return jdbcTemplate.query(sql, new ImageObjectsMapper(), imageId.toArray());
    }

    @Override
    public List<ImageObjects> getImageObjectsByObjectId(List<Integer> objectId) {
        String sql = SqlBuilder.buildInClause(GET_IMAGE_OBJECTS_BY_OBJECT_ID, objectId);
        return jdbcTemplate.query(sql, new ImageObjectsMapper(), objectId.toArray());
    }

    @Override
    public int[] insertImageObjects(List<ImageObjects> imageObjectsList) {
        return jdbcTemplate.batchUpdate(INSERT_IMAGE_OBJECTS, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ImageObjects imageToObject = imageObjectsList.get(i);
                ps.setInt(1, imageToObject.getObjectId());
                ps.setInt(2, imageToObject.getImageId());
                ps.setFloat(3, imageToObject.getScore());

            }

            @Override
            public int getBatchSize() {
                return imageObjectsList.size();
            }
        });
    }


    static class ImageObjectsMapper implements RowMapper<ImageObjects> {
        @Override
        public ImageObjects mapRow(ResultSet rs, int rowNum) throws SQLException {

            return ImageObjects.builder()
                    .imageId(rs.getInt("image_id"))
                    .objectId(rs.getInt("object_id"))
                    .score(rs.getFloat("score"))
                    .build();
        }
    }

    private static final String GET_IMAGE_OBJECTS_BY_IMAGE_ID =
            "select ios.image_object_id, " +
                    "ios.object_id, " +
                    "ios.image_id, " +
                    "ios.score " +
                    "from image_objects ios " +
                    "inner join image i on i.image_id = ios.image_id  " +
                    "where ios.image_id in (%s)";
    private static final String GET_IMAGE_OBJECTS_BY_OBJECT_ID =
            "select ios.image_object_id, " +
                    "ios.object_id, " +
                    "ios.image_id, " +
                    "ios.score " +
                    "from image_objects ios " +
                    "inner join image_object io on io.object_id = ios.object_id  " +
                    "where ios.object_id in (%s)";
    private static final String INSERT_IMAGE_OBJECTS =
            "insert into image_objects (object_id, image_id, score) values (?, ?, ?)";
}

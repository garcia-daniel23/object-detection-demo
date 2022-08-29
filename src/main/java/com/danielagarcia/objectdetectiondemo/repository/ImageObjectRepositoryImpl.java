package com.danielagarcia.objectdetectiondemo.repository;

import com.danielagarcia.objectdetectiondemo.model.ImageObject;
import com.danielagarcia.objectdetectiondemo.utility.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ImageObjectRepositoryImpl implements ImageObjectRepository {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public ImageObjectRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ImageObject> getImageObjectByObjectId(List<Integer> objectId) {
        String sql = SqlBuilder.buildInClause(GET_IMAGE_OBJECT_BY_ID, objectId);
        return jdbcTemplate.query(sql,
                new ImageObjectMapper(),
                objectId.toArray());
    }

    @Override
    public List<ImageObject> getImageObjectByJoiningImageId(List<Integer> imageId) {
        String sql = SqlBuilder.buildInClause(GET_IMAGE_OBJECT_BY_JOINING_IMAGE_ID, imageId);
        return jdbcTemplate.query(sql,
                new ImageObjectMapper(),
                imageId.toArray());
    }

    public List<ImageObject> getImageObjectByName(List<String> name) {
        String sql = SqlBuilder.buildInClause(GET_IMAGE_OBJECT_BY_NAME, name);
        return jdbcTemplate.query(sql,
                new ImageObjectMapper(),
                name.toArray());
    }

    @Override
    public int[] insertImageObject(List<ImageObject> imageObjectList) {
        return jdbcTemplate.batchUpdate(INSERT_IMAGE_OBJECT, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ImageObject imageObject = imageObjectList.get(i);
                ps.setString(1, imageObject.getName());
            }

            @Override
            public int getBatchSize() {
                return imageObjectList.size();
            }
        });
    }


    static class ImageObjectMapper implements RowMapper<ImageObject> {

        @Override
        public ImageObject mapRow(ResultSet rs, int rowNum) throws SQLException {
            return ImageObject.builder()
                    .objectId(rs.getInt("object_id"))
                    .name(rs.getString("name"))
                    .build();
        }
    }

    private static final String GET_IMAGE_OBJECT_BY_NAME =
            "select io.object_id, io.\"name\" " +
                    "from image_object io " +
                    "where io.\"name\" in (%s)";
    private static final String GET_IMAGE_OBJECT_BY_JOINING_IMAGE_ID =
            "select distinct io.object_id, io.\"name\" " +
                    "from " +
                    "image_object io " +
                    "inner join image_objects ios on " +
                    "ios.object_id = io.object_id " +
                    "inner join image i on " +
                    "i.image_id = ios.image_id " +
                    "where " +
                    "i.image_id in (%s)";
    private static final String GET_IMAGE_OBJECT_BY_ID =
            "select io.object_id, io.\"name\" " +
                    "from image_object io " +
                    "where io.object_id in (%s)";
    private static final String INSERT_IMAGE_OBJECT =
            "insert into image_object (\"name\") values (?) " +
                    "on conflict (\"name\") do nothing; ";
}

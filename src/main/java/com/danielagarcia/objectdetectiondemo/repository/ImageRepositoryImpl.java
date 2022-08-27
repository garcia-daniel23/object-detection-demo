package com.danielagarcia.objectdetectiondemo.repository;

import com.danielagarcia.objectdetectiondemo.model.Image;
import com.danielagarcia.objectdetectiondemo.model.ImageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ImageRepositoryImpl implements ImageRepository {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public ImageRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Image> getImages() {
        return jdbcTemplate.query(GET_IMAGES, new ImageMapper());
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

    static class ImageMapper implements RowMapper<Image> {
        @Override
        public Image mapRow(ResultSet rs, int rowNum) throws SQLException {

            return Image.builder()
                    .imageId(rs.getInt("image_id"))
                    .label(rs.getString("label"))
                    .imageData(rs.getString("image"))
                    .enable(rs.getBoolean("detect_flg"))
            .build();
        }
    }

    private static final String GET_IMAGES = "select i.image_id, " +
            "i.\"label\" , " +
            "i.detect_flg, " +
            "i.image " +
            "from image i " +
            "inner join image_objects io on io.image_id = i.image_id";

    private static final String GET_IMAGE_BY_ID = "";
    private static final String GET_IMAGES_WITH_OBJECTS = "";
    private static final String INSERT_IMAGE = "";
}


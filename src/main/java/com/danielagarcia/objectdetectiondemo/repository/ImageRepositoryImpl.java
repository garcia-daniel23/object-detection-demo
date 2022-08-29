package com.danielagarcia.objectdetectiondemo.repository;

import com.danielagarcia.objectdetectiondemo.model.Image;
import com.danielagarcia.objectdetectiondemo.model.ImageObject;
import com.danielagarcia.objectdetectiondemo.utility.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
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
    public List<Image> getImageById(List<Integer> imageIds) {
        String sql = SqlBuilder.buildInClause(GET_IMAGE_BY_ID, imageIds);
        return jdbcTemplate.query(sql, new ImageMapper(), imageIds.toArray());
    }

    @Override
    public Integer insertImage(Image image) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
                    PreparedStatement statement = con.prepareStatement(INSERT_IMAGE, new String[] {"image_id"});
                    statement.setString(1, image.getLabel());
                    statement.setInt(2, image.isEnable() ? 1 : 0);
                    statement.setString(3, image.getImageData());
                    return statement;
                },
                keyHolder
                );
        return keyHolder.getKey().intValue();
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

    private static final String GET_IMAGES =
            "select distinct i.image_id, " +
                    "i.\"label\" , " +
                    "i.detect_flg, " +
                    "i.image " +
                    "from image i ";

    private static final String GET_IMAGE_BY_ID =
            "select i.image_id, " +
                    "i.\"label\" , " +
                    "i.detect_flg, " +
                    "i.image " +
                    "from image i " +
                    "where i.image_id in (%s)";
    private static final String INSERT_IMAGE =
            "insert into image (\"label\", detect_flg, image) values (?, ?, ?)";
}


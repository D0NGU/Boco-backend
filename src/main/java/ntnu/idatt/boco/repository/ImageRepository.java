package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.model.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImageRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void newPicture(ProductImage image) {
        jdbcTemplate.update("INSERT INTO images(img_name, img_blob, product_id) VALUES (?,?,?);",
                new Object[] {image.getImgName(), image.getImgBlob(), image.getProductId()});
    }

    public List<ProductImage> getImagesByProductId(int productId) {
        return jdbcTemplate.query("SELECT * FROM images WHERE product_id = ?",
                BeanPropertyRowMapper.newInstance(ProductImage.class), productId);
    }
}

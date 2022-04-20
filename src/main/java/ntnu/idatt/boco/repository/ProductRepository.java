package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.model.Category;
import ntnu.idatt.boco.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CategoryRepository categoryRepository;

    public List<Product> getAll() {
        return jdbcTemplate.query("SELECT * FROM products", BeanPropertyRowMapper.newInstance(Product.class));
    }

    public List<Product> getFromCategory(String category) {
        List<Category> categories = categoryRepository.getSubCategories(category);
        String inSql = String.join(",", Collections.nCopies(categories.size(), "?"));
        return jdbcTemplate.query(String.format("SELECT * FROM products WHERE (%s)", inSql), BeanPropertyRowMapper.newInstance(Product.class), categories.toArray());
    }

    public Product getProduct(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM products WHERE product_id", BeanPropertyRowMapper.newInstance(Product.class), id);
    }
}

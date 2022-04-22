package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.model.Category;
import ntnu.idatt.boco.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CategoryRepository categoryRepository;

    public void newProduct(Product product) {
        jdbcTemplate.update("INSERT INTO products(name, description, address, price, unlisted, available_from, available_to, user_id, category) VALUES (?,?,?,?,?,?,?,?,?)",
                new Object[] {product.getName(), product.getDescription(), product.getAddress(), product.getPrice(), product.isUnlisted(), product.getAvailableFrom(), product.getAvailableTo(), product.getUserId(), product.getCategory()});
    }

    public void editProduct(Product product, String productId) {
        jdbcTemplate.update("UPDATE products SET description=?, address=?, price=?, unlisted=?, category=? WHERE product_id=?",
                new Object[] {product.getDescription(), product.getAddress(), product.getPrice(), product.isUnlisted(), product.getCategory(), productId});
    }

    public List<Product> getAll() {
        return jdbcTemplate.query("SELECT * FROM products", BeanPropertyRowMapper.newInstance(Product.class));
    }

    public List<Product> getFromCategory(String category) {
        List<Category> categories = categoryRepository.getSubCategories(category, categoryRepository.getAll());
        List<Object> catNames = new ArrayList<>();
        for (Category cat : categories) {
            catNames.add(cat.getCategory());
        }
        String inSql = String.join(",", Collections.nCopies(categories.size(), "?"));
        return jdbcTemplate.query(String.format("SELECT * FROM products WHERE category IN (%s)", inSql), BeanPropertyRowMapper.newInstance(Product.class), catNames.toArray());
    }

    public Product getProduct(String productId) {
        return jdbcTemplate.queryForObject("SELECT * FROM products WHERE product_id = ?", BeanPropertyRowMapper.newInstance(Product.class), productId);
    }

    public List<Product> getFromUserId(String productId) {
        return jdbcTemplate.query("SELECT * FROM products WHERE user_id = ?", BeanPropertyRowMapper.newInstance(Product.class), productId);
    }
}

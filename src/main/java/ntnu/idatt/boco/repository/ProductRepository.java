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

    public void newProduct(Product product) {
        jdbcTemplate.update("INSERT INTO products(title, description, address, price, unlisted, user_id, category) VALUES (?,?,?,?,?,?,?)",
                new Object[] {product.getName(), product.getDescription(), product.getAddress(), product.getPrice(), product.isUnlisted(), product.getUserId(), product.getCategory()});
    }

    public void editProduct(Product product, String id) {
        jdbcTemplate.update("UPDATE products SET description=?, address=?, price=?, unlisted=?, category=? WHERE product_id=?",
                new Object[] {product.getDescription(), product.getAddress(), product.getPrice(), product.isUnlisted(), product.getCategory(), id});
    }

    public List<Product> getAll() {
        return jdbcTemplate.query("SELECT * FROM products", BeanPropertyRowMapper.newInstance(Product.class));
    }

    public List<Product> getFromCategory(String category) {
        List<Category> categories = categoryRepository.getSubCategories(category, categoryRepository.getAll());
        String inSql = String.join(",", Collections.nCopies(categories.size(), "?"));
        return jdbcTemplate.query(String.format("SELECT * FROM products WHERE (%s)", inSql), BeanPropertyRowMapper.newInstance(Product.class), categories.toArray());
    }

    public Product getProduct(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM products WHERE product_id = ?", BeanPropertyRowMapper.newInstance(Product.class), id);
    }

    public List<Product> getFromUserId(String id) {
        return jdbcTemplate.query("SELECT * FROM products WHERE user_id = ?", BeanPropertyRowMapper.newInstance(Product.class));
    }

    public List<Product> searchProductByWord(String word) {
        String sql = "SELECT product_id,title,description,address,price,unlisted,available_from,available_to,user_id,category FROM products LEFT JOIN (FT_SEARCH_DATA('"+word+"', 0, 0)) ON products.product_id=keys[1] WHERE keys IS NOT NULL;";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class));
    }
}

package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.model.Category;
import ntnu.idatt.boco.model.Listing;
import ntnu.idatt.boco.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is responsible for communication with the database regarding products.
 */
@Repository
public class ProductRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CategoryRepository categoryRepository;

    /**
     * Method for adding a new product to the database
     * @param product the product to be added
     */
    public void newProduct(Product product) {
        jdbcTemplate.update("INSERT INTO products(title, description, address, price, unlisted, available_from, available_to, user_id, category) VALUES (?,?,?,?,?,?,?,?,?)",
                new Object[] {product.getTitle(), product.getDescription(), product.getAddress(), product.getPrice(), product.isUnlisted(), product.getAvailableFrom(), product.getAvailableTo(), product.getUserId(), product.getCategory()});
    }

    /**
     * Method for editing a product in the database
     * @param product the edited product
     * @param productId the id of the product to edit
     */
    public void editProduct(Product product, int productId) {
        jdbcTemplate.update("UPDATE products SET description=?, address=?, price=?, unlisted=?, category=? WHERE product_id=?",
                new Object[] {product.getDescription(), product.getAddress(), product.getPrice(), product.isUnlisted(), product.getCategory(), productId});
    }

    /**
     * Method for retrieving all the products in the database
     * @return a list of all the products in the database
     */
    public List<Product> getAll() {
        return jdbcTemplate.query("SELECT * FROM products", BeanPropertyRowMapper.newInstance(Product.class));
    }

    /**
     * Method for retrieving all products of a certain category
     * @param category the category of the products
     * @return a list of all the products of a certain category
     */
    public List<Product> getFromCategory(String category) {
        List<Category> categories = categoryRepository.getSubCategories(category, categoryRepository.getAll());
        List<Object> catNames = new ArrayList<>();
        for (Category cat : categories) {
            catNames.add(cat.getCategory());
        }
        String inSql = String.join(",", Collections.nCopies(categories.size(), "?"));
        return jdbcTemplate.query(String.format("SELECT * FROM products WHERE category IN (%s)", inSql), BeanPropertyRowMapper.newInstance(Product.class), catNames.toArray());
    }

    /**
     * Method for retrieving a product by id
     * @param productId the id of the product
     * @return the product with the id
     */
    public Product getProduct(int productId) {
        return jdbcTemplate.queryForObject("SELECT * FROM products WHERE product_id = ?", BeanPropertyRowMapper.newInstance(Product.class), productId);
    }

    /**
     * Method for retrieving all of a users products
     * @param userId the id of the user to retrieve the products for
     * @return a list of all the users products
     */
    public List<Product> getFromUserId(int userId) {
        return jdbcTemplate.query("SELECT * FROM products WHERE user_id = ?", BeanPropertyRowMapper.newInstance(Product.class), userId);
    }

    /**
     * Method for searching for products
     * @param word the word to search for
     * @return a list of all the products matching the search-word
     */
    public List<Product> searchProductByWord(String word) {
        String sql = "SELECT product_id,title,description,address,price,unlisted,available_from,available_to,user_id,category FROM products LEFT JOIN (FT_SEARCH_DATA('"+word+"', 0, 0)) ON products.product_id=keys[1] WHERE keys IS NOT NULL;";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class));
    }
}

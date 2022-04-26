package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.model.Category;
import ntnu.idatt.boco.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
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
    Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    Environment env;

    /**
     * Method for adding a new product to the database
     *
     * @param product the product to be added
     */
    public void newProduct(Product product) {
        System.out.println(product.getUserId());
        jdbcTemplate.update("INSERT INTO products(title, description, address, price, unlisted, available_from, available_to, user_id, category) VALUES (?,?,?,?,?,?,?,?,?)",
                new Object[]{product.getTitle(), product.getDescription(), product.getAddress(), product.getPrice(), product.isUnlisted(), product.getAvailableFrom(), product.getAvailableTo(), product.getUserId(), product.getCategory()});
    }

    /**
     * Method for editing a product in the database
     *
     * @param product   the edited product
     * @param productId the id of the product to edit
     */
    public void editProduct(Product product, int productId) {
        jdbcTemplate.update("UPDATE products SET description=?, address=?, price=?, unlisted=?, category=? WHERE product_id=?",
                new Object[]{product.getDescription(), product.getAddress(), product.getPrice(), product.isUnlisted(), product.getCategory(), productId});
    }

    /**
     * Method for retrieving all the products in the database
     *
     * @return a list of all the products in the database
     */
    public List<Product> getAll(int offset, String sortBy, boolean ascending) {
        String order = "";
        if (ascending) {
            order = "ASC";
        } else {
            order = "DESC";
        }
        return jdbcTemplate.query("SELECT * FROM products ORDER BY " + sortBy + " " + order + " LIMIT 10 OFFSET ? ", BeanPropertyRowMapper.newInstance(Product.class), new Object[]{offset});
    }

    /**
     * Method for retrieving a product by id
     *
     * @param productId the id of the product
     * @return the product with the id
     */
    public Product getProduct(int productId) {
        logger.info("Finding product " + productId);
        return jdbcTemplate.queryForObject("SELECT * FROM products WHERE product_id = ?", BeanPropertyRowMapper.newInstance(Product.class), productId);
    }

    /**
     * Method for retrieving all of a users products
     *
     * @param userId the id of the user to retrieve the products for
     * @return a list of all the users products
     */
    public List<Product> getFromUserId(int userId) {
        return jdbcTemplate.query("SELECT * FROM products WHERE user_id = ?", BeanPropertyRowMapper.newInstance(Product.class), userId);
    }

    /**
     * Method for searching for products by search word
     *
     * @param word      the word to search for
     * @param offset    how many rows to skip
     * @param sortBy    what to sort the list by
     * @param ascending true if sort order is ascending, false for descending
     * @return a list of all the products matching the search-word
     */
    public List<Product> searchProductByWord(String word, int offset, String sortBy, boolean ascending) {
        String order = "";
        if (ascending) {
            order = "ASC";
        } else {
            order = "DESC";
        }
        if (env.acceptsProfiles(Profiles.of("mysql"))) {
            return jdbcTemplate.query("SELECT * FROM products WHERE MATCH (title, description) AGAINST (? IN NATURAL LANGUAGE MODE) ORDER BY " + sortBy + " " + order + " LIMIT 10 OFFSET ?", BeanPropertyRowMapper.newInstance(Product.class), new Object[]{word, offset});
        } else {
            String sql = "SELECT product_id,title,description,address,price,unlisted,available_from,available_to,user_id,category FROM products LEFT JOIN (FT_SEARCH_DATA('" + word + "', 10, ?)) ON products.product_id=keys[1] WHERE keys IS NOT NULL ORDER BY " + sortBy + " " + order + ";";
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class), new Object[]{offset});
        }
    }

    /**
     * Method for searching for products by search word and category
     *
     * @param word      the word to search for
     * @param category  the category to search for
     * @param offset    how many rows to skip
     * @param sortBy    what to sort the list by
     * @param ascending true if sort order is ascending, false for descending
     * @return a list of all the products matching the search-word and category
     */
    public List<Product> searchProductByWordAndCategory(String word, String category, int offset, String sortBy, boolean ascending) {
        String order = "";
        if (ascending) {
            order = "ASC";
        } else {
            order = "DESC";
        }
        List<Category> categories = categoryRepository.getSubCategories(category, categoryRepository.getAll());
        List<Object> catNames = new ArrayList<>();
        for (Category cat : categories) {
            catNames.add(cat.getCategory());
        }
        String inSql = String.join(",", Collections.nCopies(categories.size(), "?"));
        if (env.acceptsProfiles(Profiles.of("mysql"))) {
            return jdbcTemplate.query(String.format("SELECT * FROM products WHERE MATCH (title, description) AGAINST (%s IN NATURAL LANGUAGE MODE)" +
                    " AND category IN (%s) ORDER BY %s %s LIMIT 10 OFFSET %s", word, inSql, sortBy, order, offset), BeanPropertyRowMapper.newInstance(Product.class), catNames.toArray());
        } else {
            return jdbcTemplate.query(String.format("SELECT product_id,title,description,address,price,unlisted,available_from,available_to,user_id,category FROM products LEFT JOIN (FT_SEARCH_DATA('" + word + "', 10, %s)) ON products.product_id=keys[1] WHERE keys IS NOT NULL" +
                    " AND category IN (%s) ORDER BY %s %s", offset, inSql, sortBy, order), BeanPropertyRowMapper.newInstance(Product.class), catNames.toArray());
        }
    }

    /**
     * Method for searching for products by category
     *
     * @param category  the category to search for
     * @param offset    how many rows to skip
     * @param sortBy    what to sort the list by
     * @param ascending true if sort order is ascending, false for descending
     * @return a list of all the products matching the category
     */
    public List<Product> searchProductByCategory(String category, int offset, String sortBy, boolean ascending) {
        String order = "";
        if (ascending) {
            order = "ASC";
        } else {
            order = "DESC";
        }
        List<Category> categories = categoryRepository.getSubCategories(category, categoryRepository.getAll());
        List<Object> catNames = new ArrayList<>();
        for (Category cat : categories) {
            catNames.add(cat.getCategory());
        }
        String inSql = String.join(",", Collections.nCopies(categories.size(), "?"));
        return jdbcTemplate.query(String.format("SELECT * FROM products WHERE category IN (%s) ORDER BY %s %s LIMIT 10 OFFSET %s", inSql, sortBy, order, offset), BeanPropertyRowMapper.newInstance(Product.class), catNames.toArray());
    }
}



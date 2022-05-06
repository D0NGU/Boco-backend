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
        jdbcTemplate.update("INSERT INTO products(title, description, address, price, unlisted, available_from, available_to, user_id, category, tlf) VALUES (?,?,?,?,?,?,?,?,?,?)",
                new Object[]{product.getTitle(), product.getDescription(), product.getAddress(), product.getPrice(), product.isUnlisted(), product.getAvailableFrom(), product.getAvailableTo(), product.getUserId(), product.getCategory(), product.getTlf()});
    }

    /**
     * Method for editing a product in the database
     *
     * @param product   the edited product
     * @param productId the id of the product to edit
     */
    public void editProduct(Product product, int productId) {
        jdbcTemplate.update("UPDATE products SET description=?, address=?, price=?, unlisted=?, category=?, tlf=? WHERE product_id=?",
                new Object[]{product.getDescription(), product.getAddress(), product.getPrice(), product.isUnlisted(), product.getCategory(), product.getTlf(),productId});
    }

    /**
     * Method for retrieving all the products in the database
     *
     * @return a list of all the products in the database
     */
    public List<Product> getAll(String sortBy, boolean ascending) {
        String order = "";
        if (ascending) {
            order = "ASC";
        } else {
            order = "DESC";
        }
        return jdbcTemplate.query("SELECT * FROM products WHERE unlisted = false ORDER BY " + sortBy + " " + order , BeanPropertyRowMapper.newInstance(Product.class));
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

    public Product getProductByTitle(String title) {
        logger.info("Finding product " + title);
        return jdbcTemplate.queryForObject("SELECT * FROM products WHERE title = ?", BeanPropertyRowMapper.newInstance(Product.class), title);
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
     * @param sortBy    what to sort the list by
     * @param ascending true if sort order is ascending, false for descending
     * @return a list of all the products matching the search-word
     */
    public List<Product> searchProductByWord(String word, String sortBy, boolean ascending) {
        String order = "";
        if (ascending) {
            order = "ASC";
        } else {
            order = "DESC";
        }
        if (env.acceptsProfiles(Profiles.of("mysql"))) {
            return jdbcTemplate.query("SELECT * FROM products WHERE MATCH (title, description) AGAINST ('?' IN NATURAL LANGUAGE MODE) AND unlisted = false ORDER BY " + sortBy + " " + order , BeanPropertyRowMapper.newInstance(Product.class), new Object[]{word});
        } else {
            String sql = "SELECT product_id,title,description,address,price,unlisted,available_from,available_to,user_id,category FROM products LEFT JOIN (FT_SEARCH_DATA('" + word + "', 0, 0)) ON products.product_id=keys[1] WHERE keys IS NOT NULL AND unlisted = false ORDER BY " + sortBy + " " + order + ";";
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class));
        }
    }

    /**
     * Method for searching for products by search word and category
     *
     * @param word      the word to search for
     * @param category  the category to search for
     * @param sortBy    what to sort the list by
     * @param ascending true if sort order is ascending, false for descending
     * @return a list of all the products matching the search-word and category
     */
    public List<Product> searchProductByWordAndCategory(String word, String category, String sortBy, boolean ascending) {
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
            return jdbcTemplate.query(String.format("SELECT * FROM products WHERE MATCH (title, description) AGAINST ('%s' IN NATURAL LANGUAGE MODE)" +
                    " AND category IN (%s) AND unlisted = false ORDER BY %s %s", word, inSql, sortBy, order), BeanPropertyRowMapper.newInstance(Product.class), catNames.toArray());
        } else {
            return jdbcTemplate.query(String.format("SELECT product_id,title,description,address,price,unlisted,available_from,available_to,user_id,category FROM products LEFT JOIN (FT_SEARCH_DATA('" + word + "', 0, 0)) ON products.product_id=keys[1] WHERE keys IS NOT NULL AND unlisted = false" +
                    " AND category IN (%s) ORDER BY %s %s", inSql, sortBy, order), BeanPropertyRowMapper.newInstance(Product.class), catNames.toArray());
        }
    }

    /**
     * Method for searching for products by category
     *
     * @param category  the category to search for
     * @param sortBy    what to sort the list by
     * @param ascending true if sort order is ascending, false for descending
     * @return a list of all the products matching the category
     */
    public List<Product> searchProductByCategory(String category, String sortBy, boolean ascending) {
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
        return jdbcTemplate.query(String.format("SELECT * FROM products WHERE category IN (%s) AND unlisted = false ORDER BY %s %s", inSql, sortBy, order), BeanPropertyRowMapper.newInstance(Product.class), catNames.toArray());
    }

    /**
     * Method for retrieving a user rental history
     *
     * @param userId  the id of the user
     * @return a list of all the products a user has rented
     */
    public List<Product> getUserRentalHistory(int userId) {
        return jdbcTemplate.query("SELECT products.product_id, products.title, products.description, products.address, products.price, products.unlisted, products.available_from, products.available_to, products.user_id, products.category FROM products RIGHT OUTER JOIN rentals ON products.product_id = rentals.product_id WHERE rentals.user_id = ? AND accepted = true",
                BeanPropertyRowMapper.newInstance(Product.class), userId);
    }

    /**
     * Method to delete product based on product id and user id
     * @param userId the d of a user
     * @param productId the id of a product that is going to be deleted
     * @return amount of rows altered (expected to be one 1 if the deletion was successful)
     */
    public int deleteProductWithUserIdAndProductId(int userId, int productId){
        return jdbcTemplate.update("DELETE FROM products WHERE product_id = ? AND user_id = ?;", productId, userId);
    }
}



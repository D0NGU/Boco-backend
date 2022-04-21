package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.model.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class contains methods relating to registering and retrieving rentals to/from the database.
 */
@Repository
public class RentalRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Returns a list of all rentals with a certain product_id.
     * @param productId the product_id of the rentals to be retrieved
     * @return a list containing all rentals with the correct product_id
     */
    public List<Rental> getRentals(String productId) {
        return jdbcTemplate.query("SELECT * FROM rentals WHERE product_id = ?;", BeanPropertyRowMapper.newInstance(Rental.class), productId);
    }

    /**
     * Method for saving a new rental object to the database.
     * @param rental the rental object to be saved to the database
     * @return the number of rows in the database that was affected by the SQL insertion
     */
    public int saveRentalToDatabase(Rental rental) {
        return jdbcTemplate.update("INSERT INTO rentals (date_from, date_to, product_id, user_id) VALUES (?,?,?,?);",
                new Object[] { rental.getDateFrom(), rental.getDateTo(), rental.getProductId(), rental.getUserId()});
    }
}

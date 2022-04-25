package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.model.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class contains methods relating to managing rentals in the database.
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
    public List<Rental> getRentals(int productId) {
        return jdbcTemplate.query("SELECT * FROM rentals WHERE product_id = ? ORDER BY date_from;", BeanPropertyRowMapper.newInstance(Rental.class), productId);
    }

    /**
     * Returns a list of all accepted or non-accepted rentals with a certain product_id.
     * @param productId the product_id of the rentals to be retrieved
     * @param accepted true to retrieve all accepted rentals, false to retrieve all non-accepted rentals
     * @return a list containing all accepted or non-accepted rentals with the correct product_id
     */
    public List<Rental> getAcceptedRentals(int productId, boolean accepted) {
        return jdbcTemplate.query("SELECT * FROM rentals WHERE product_id = ? AND accepted = ? ORDER BY date_from;", BeanPropertyRowMapper.newInstance(Rental.class), productId, accepted);
    }

    /**
     * Method for saving a new rental object to the database.
     * @param rental the rental object to be saved to the database
     * @return the number of rows in the database that was affected by the SQL insertion
     */
    public int saveRentalToDatabase(Rental rental) {
        return jdbcTemplate.update("INSERT INTO rentals (date_from, date_to, accepted, product_id, user_id) VALUES (?,?,?,?,?);",
                new Object[] { rental.getDateFrom(), rental.getDateTo(), rental.isAccepted(), rental.getProductId(), rental.getUserId()});
    }

    /**
     * Method for deleting a rental object to the database.
     * @param rentalId the rental object to be deleted from the database
     * @return the number of rows in the database that was affected
     */
    public int deleteRental(int rentalId) {
        return jdbcTemplate.update("DELETE FROM rentals WHERE rental_id = ?;", rentalId);
    }
}

package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.model.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RentalRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Rental> getRentals(String productId) {
        return jdbcTemplate.query("SELECT * FROM rentals WHERE product_id = ?;", BeanPropertyRowMapper.newInstance(Rental.class), productId);
    }

    public int saveRentalToDatabase(Rental rental) {
        return jdbcTemplate.update("INSERT INTO rentals (date_from, date_to, product_id, user_id) VALUES (?,?,?,?);",
                new Object[] { rental.getDateFrom(), rental.getDateTo(), rental.getProductId(), rental.getUserId()});
    }
}

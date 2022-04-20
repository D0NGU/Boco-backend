package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.model.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RentalRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int saveRentalToDatabase(Rental rental) {
        return jdbcTemplate.update("INSERT INTO rentals (rental_id, date_from, date_to, product_id, user_id) VALUES (?,?,?,?,?);",
                new Object[] { rental.getRentalId(), rental.getDateFrom(), rental.getDateTo(), rental.getProductId(), rental.getUserId()});
    }
}

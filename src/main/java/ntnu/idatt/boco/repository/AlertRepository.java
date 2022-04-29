package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.model.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class is responsible for communication with the database regarding alerts.
 */
@Repository
public class AlertRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Method for adding an alert to the database
     * @param alert the new alert
     * @return the number of rows in the database that was affected by the insertion
     */
    public int newAlert(Alert alert) {
        return jdbcTemplate.update("INSERT INTO alerts (description, alert_date, has_seen, optional_id, user_id) VALUES (?,?,?,?,?)",
                new Object[]{ alert.getDescription(), alert.getAlertDate(), alert.isHasSeen(), alert.getOptionalId(), alert.getUserId() });
    }

    /**
     * Method for retrieving a users alerts
     * @param userId the id of the user to retrieve alerts for
     * @return a list of all the user alerts
     */
    public List<Alert> getAlertByUserId(int userId) {
        return jdbcTemplate.query("SELECT * FROM alerts WHERE user_id = ?", BeanPropertyRowMapper.newInstance(Alert.class), userId);
    }

    /**
     * Method for retrieving a users unseen alerts
     * @param userId the id of the user to retrieve alerts for
     * @return a list of all the user alerts
     */
    public List<Alert> getUnseenAlertsByUserId(int userId) {
        return jdbcTemplate.query("SELECT * FROM alerts WHERE user_id = ? AND has_seen = false", BeanPropertyRowMapper.newInstance(Alert.class), userId);
    }

    /**
     * Method for changing an alert to "seen"
     * @param alertId the id of the alert to change
     * @return the number of rows in the database that was affected
     */
    public int changeAlertToSeen(int alertId) {
        return jdbcTemplate.update("UPDATE alerts SET has_seen = true WHERE alert_id = ?", BeanPropertyRowMapper.newInstance(Alert.class), alertId);
    }

    /**
     * Method for deleting an alert
     * @param alertId the id of the alert to delete
     * @return the number of rows in the database that was affected by the deletion
     */
    public int deleteAlert(int alertId) {
        return jdbcTemplate.update("DELETE FROM alerts WHERE alert_id = ?;", alertId);
    }
}

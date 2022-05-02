package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.Alert;
import ntnu.idatt.boco.repository.AlertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractQueue;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/alerts")
public class AlertController {
    Logger logger = LoggerFactory.getLogger(AlertController.class);
    @Autowired
    AlertRepository alertRepository;

    /**
     * Method for handling GET-requests for retrieving all alerts for a user
     * @param userId the id of the user to retrieve alerts for
     * @return an HTTP response containing a list of the alerts and an HTTP status code
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Alert>> getUserAlerts(@PathVariable("userId") int userId) {
        try {
            List<Alert> resultList = alertRepository.getAlertByUserId(userId);
            if (resultList.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            logger.info("Success - alerts retrieved");
            return new ResponseEntity<>(resultList, HttpStatus.OK);
        } catch(Exception e) {
            logger.error("Alert retrieval error");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user/{userId}/newAlert")
    public ResponseEntity<String> newAlert(@RequestBody Alert alert){
        try{
            logger.info("new alert");
            alertRepository.newAlert(alert);
            return new ResponseEntity<>("Alert created successfully", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling GET-requests for retrieving all of a users unseen alerts
     * @param userId the id of the user
     * @return an HTTP response containing a list of unseen alerts and an HTTP status code
     */
    @GetMapping("/user/{userId}/unseen")
    public ResponseEntity<List<Alert>> getUnSeenAlerts(@PathVariable int userId){
        logger.info("New GET-request for unseen alerts for user " + userId);
        try {
            List<Alert> resultList = alertRepository.getUnseenAlertsByUserId(userId);
            if (resultList.isEmpty()) {
                logger.info("No alerts that are unseen with user_id " + userId + " found");
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            logger.info("Success - unseen alerts retrieved");
            return new ResponseEntity<>(resultList, HttpStatus.OK);
        } catch(Exception e) {
            logger.error("Unseen alert retrieval error");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling PUT-requests for marking an alert as seen
     * @param alertId the id of the alert to change
     * @return an HTTP response containing a string with the status of the change and an HTTP status code
     */
    @PutMapping("/seen/{alertId}")
    public ResponseEntity<String> changeAlertToSeen(@PathVariable int alertId) {
        logger.info("PUT-request for marking alert " + alertId + " as seen.");
        try {
            if (alertRepository.changeAlertToSeen(alertId) == 1) {
                logger.info("Alert " + alertId + " was successfully marked as seen");
                return new ResponseEntity<>("Alert was successfully changed.", HttpStatus.OK);
            } else {
                logger.info("No alert with alertId " + alertId + " was found.");
                return new ResponseEntity<>("Alert could not be changed.", HttpStatus.OK);
            }
        } catch(Exception e) {
            logger.error("Error marking alert as seen");
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling DELETE-requests for deleting alerts from the database.
     * @param alertId the id of the alert to delete
     * @return an HTTP response containing a string with the status of the deletion and an HTTP status code
     */
    @DeleteMapping("/{alertId}")
    public ResponseEntity<String> deleteAlert(@PathVariable int alertId) {
        logger.info("Delete request for alert " + alertId);
        try {
            if (alertRepository.deleteAlert(alertId) == 1) {
                logger.info("Deletion of alert " + alertId + " was successful");
                return new ResponseEntity<>("Deletion was successful", HttpStatus.OK);
            } else {
                logger.info("Deletion of alert " + alertId + " was unsuccessful. No alert with id = " + alertId + " was found.");
                return new ResponseEntity<>("Deletion was unsuccessful", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Deletion failed");
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

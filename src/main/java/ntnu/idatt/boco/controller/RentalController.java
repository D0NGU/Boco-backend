package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.Rental;
import ntnu.idatt.boco.repository.RentalRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class contains methods responsible for handling HTTP requests regarding rentals.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    RentalRepository rentalRepository;

    /**
     * Method for handling GET-requests for retrieving all rentals with a certain product_id.
     * @param id the product_id of the rentals to be retrieved
     * @return an HTTP response containing a list of all rentals with the correct product_id and a HTTP status code
     */
    @GetMapping("/product/{id}")
    public ResponseEntity<List<Rental>> getRentals(@PathVariable String id) {
        logger.info("New GET-request for rentals with product_id " + id);
        try {
            List<Rental> resultList = rentalRepository.getRentals(id);
            if (resultList.isEmpty()) {
                logger.info("No rentals with product_id " + id + " found");
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            logger.info("Success - rentals retrieved");
            return new ResponseEntity<>(resultList, HttpStatus.OK);
        } catch(Exception e) {
            logger.error("Rental retrieval error", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling POST-requests for registering new rentals to the database.
     * @param rental the rental object to be saved to the database
     * @return an HTTP response containing a string with the status of the registration and a HTTP status code
     */
    @PostMapping("/")
    public ResponseEntity<String> registerNewRental(@RequestBody Rental rental) {
        logger.info("New rental registration requested");
        try {
            rentalRepository.saveRentalToDatabase(rental);
            logger.info("Success - rental registered");
            return new ResponseEntity<>("Registered successfully!", HttpStatus.CREATED);
        } catch(Exception e) {
            logger.error("Rental registration error", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
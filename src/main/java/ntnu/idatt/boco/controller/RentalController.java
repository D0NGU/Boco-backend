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
    public ResponseEntity<List<Rental>> getRentals(@PathVariable("id") int id) {
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
            logger.info("Rental retrieval error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling GET-requests for retrieving all accepted or non-accepted rentals with a certain product_id.
     * @param id the product_id of the rentals to be retrieved
     * @param accepted true if product rental is accepted, false otherwise
     * @return an HTTP response containing a list of all accepted or non-accepted rentals with the correct product_id and a HTTP status code
     */
    @GetMapping("/product/{id}/{accepted}")
    public ResponseEntity<List<Rental>> getAcceptedRentals(@PathVariable("id") int id, @PathVariable("accepted") boolean accepted) {
        if (accepted) logger.info("New GET-request for accepted rentals with product_id " + id);
        else logger.info("New GET-request for non-accepted rentals with product_id " + id);
        try {
            List<Rental> resultList = rentalRepository.getAcceptedRentals(id, accepted);
            if (resultList.isEmpty()) {
                logger.info("No rentals with product_id " + id + " found");
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            logger.info("Success - rentals retrieved");
            return new ResponseEntity<>(resultList, HttpStatus.OK);
        } catch(Exception e) {
            logger.info("Rentals retrieval error");
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
            logger.info("Rental registration error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
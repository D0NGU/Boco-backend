package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.AvailabilityWindow;
import ntnu.idatt.boco.model.Product;
import ntnu.idatt.boco.model.Rental;
import ntnu.idatt.boco.repository.ProductRepository;
import ntnu.idatt.boco.repository.RentalRepository;

import ntnu.idatt.boco.service.ProductService;
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
    Logger logger = LoggerFactory.getLogger(RentalController.class);
    @Autowired RentalRepository rentalRepository;
    @Autowired ProductRepository productRepository;
    @Autowired ProductService service;

    /**
     * Method for handling POST-requests for registering new rentals to the database.
     * @param rental the rental object to be saved to the database
     * @return an HTTP response containing a string with the status of the registration and a HTTP status code
     */
    @PostMapping
    public ResponseEntity<String> registerNewRental(@RequestBody Rental rental) {
        logger.info("New rental registration requested");
        try {
            if(checkIfAvailable(rental)) {
                rentalRepository.saveRentalToDatabase(rental);
                logger.info("Success - rental registered");
                return new ResponseEntity<>("Registered successfully!", HttpStatus.CREATED);
            }else{
                logger.info("Rental not available");
                return new ResponseEntity<>("Rental unavailable", HttpStatus.CONFLICT);
            }

        } catch(Exception e) {
            logger.info("Rental registration error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
     * @param accepted true to retrieve all accepted rentals, false to retrieve all non-accepted rentals
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
     * Method for handling POST-requests for accepting rentals.
     * @param rental the rental object to be accepted
     * @return an HTTP response containing a string with the status of the change and a HTTP status code
     */
    @PutMapping("/accept")
    public ResponseEntity<String> acceptRental(@RequestBody Rental rental) {
        logger.info("Accept request for rental " + rental.getRentalId());
        try {
            if (checkIfAvailable(rental)) {
                rentalRepository.acceptRental(rental.getRentalId());
                logger.info("Rental " + rental.getRentalId() + " was successfully accepted");
                return new ResponseEntity<>("Acceptance was successful", HttpStatus.OK);
            } else {
                logger.info("Rental " + rental.getRentalId() + " could not be accepted due to date conflict");
                return new ResponseEntity<>("Acceptance was unsuccessful", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            logger.info("Acceptance failed", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling DELETE-requests for deleting rentals from the database.
     * @param rentalId the id of the rental to delete
     * @return an HTTP response containing a string with the status of the deletion and a HTTP status code
     */
    @DeleteMapping("/{rentalId}")
    public ResponseEntity<String> deleteRental(@PathVariable int rentalId) {
        logger.info("Delete request for rental " + rentalId);
        try {
            if (rentalRepository.deleteRental(rentalId) == 1) {
                logger.info("Deletion of rental " + rentalId + " was successful");
                return new ResponseEntity<>("Deletion was successful", HttpStatus.OK);
            } else {
                logger.info("Deletion of rental " + rentalId + " was unsuccessful. No rental with id = " + rentalId + " was found.");
                return new ResponseEntity<>("Deletion was unsuccessful", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.info("Deletion failed", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for checking if a rental has a valid start and end date
     * @param rental the rental to check
     * @return true if start and end dates are valid, false otherwise
     */
    private boolean checkIfAvailable(Rental rental) {
        boolean availableSpot = false;
        Product test = productRepository.getProduct(rental.getProductId());
        List<Rental> rentals = rentalRepository.getAcceptedRentals(test.getProductId(), true);
        List<AvailabilityWindow> availabilityWindows = service.getAvailability(test,rentals);
        for (AvailabilityWindow availabilityWindow : availabilityWindows){
            if(!rental.getDateFrom().isBefore(availabilityWindow.getFrom()) && !rental.getDateTo().isAfter(availabilityWindow.getTo())){
                availableSpot = true;
            }
        }
        return availableSpot;
    }
}
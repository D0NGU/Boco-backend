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

@CrossOrigin
@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    RentalRepository rentalRepository;

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
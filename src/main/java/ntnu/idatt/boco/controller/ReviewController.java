package ntnu.idatt.boco.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ntnu.idatt.boco.model.Review;
import ntnu.idatt.boco.repository.ReviewRepository;

@CrossOrigin
@RestController
@RequestMapping("api/review")
public class ReviewController {
    Logger logger = LoggerFactory.getLogger(RentalController.class);
    @Autowired ReviewRepository reviewRepository;

    @PostMapping
    public ResponseEntity<?> registerNewReview(@RequestBody Review review) {
        logger.info("New review received");
        try {
            reviewRepository.newReview(review);
            return new ResponseEntity<>("Review created", HttpStatus.CREATED);
        } 
        catch(DataIntegrityViolationException dve) {
            logger.warn("Error saving review - stars not within boundaries");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(Exception e) {
            logger.error("Error saving review");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{reviewId}")
    @ResponseBody
    public ResponseEntity<Review> getById(@PathVariable int reviewId) {
        logger.info("Getting review " + reviewId);
        try {
            return new ResponseEntity<>(reviewRepository.getReview(reviewId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting product");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
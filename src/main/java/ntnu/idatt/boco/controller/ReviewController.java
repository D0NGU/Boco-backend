package ntnu.idatt.boco.controller;

import java.time.LocalDate;
import java.util.List;

import ntnu.idatt.boco.model.Alert;
import ntnu.idatt.boco.repository.AlertRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired AlertRepository alertRepository;

    /**
     * Endpoint for adding a new review
     * @param review {@link Review} object
     * @return message and HttpStatus
     */
    @PostMapping
    public ResponseEntity<String> registerNewReview(@RequestBody Review review) {
        logger.info("New review received");
        try {
            // Try to add a new review to database
            reviewRepository.newReview(review);
            alertRepository.newAlert(new Alert(1, "Du har fått en ny anmeldelse!", LocalDate.now(), false, review.getSubject()));
            return new ResponseEntity<>("Review created", HttpStatus.CREATED);
        }
        catch(DataIntegrityViolationException dve) {
            // Check if stars are 1-5
            logger.warn("Error saving review - stars not within boundaries");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(Exception e) {
            logger.error("Error saving review");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint for getting a single {@link Review} from reviewId
     * @param reviewId
     * @return a {@link Review}
     */
    @GetMapping("/{reviewId}")
    @ResponseBody
    public ResponseEntity<Review> getById(@PathVariable int reviewId) {
        logger.info("Getting review " + reviewId);
        try {
            return new ResponseEntity<>(reviewRepository.getReview(reviewId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting review");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint for getting reviews from database. Can get reviews based on the 
     * person writing the review, the one getting reviewed, both at once or neither. 
     * @param subject the userId of the person getting reviewed
     * @param author the userId of the person writing the review
     * @return list of reviews
     * @see Review
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Review>> getReviewList(@RequestParam(required=false) Integer subject, @RequestParam(required=false) Integer author) {
        try {
            if (subject == null && author != null) {
                logger.info("Getting reviews: By author");
                return new ResponseEntity<>(reviewRepository.getAllReviewsByAuthor(author), HttpStatus.OK);
            } else if (subject != null && author == null) {
                logger.info("Getting reviews: By subject");
                return new ResponseEntity<>(reviewRepository.getAllReviewsBySubject(subject), HttpStatus.OK);
            } else if (subject != null && author != null) {
                logger.info("Getting reviews: By author & subject");
                return new ResponseEntity<>(reviewRepository.getReviewsByAuthorSubject(author, subject), HttpStatus.OK);
            } else {
                logger.info("Getting all reviews");
                return new ResponseEntity<>(reviewRepository.getAllReviews(), HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error getting reviews");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
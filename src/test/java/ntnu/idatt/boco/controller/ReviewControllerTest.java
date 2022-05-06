package ntnu.idatt.boco.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import ntnu.idatt.boco.model.Review;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest
public class ReviewControllerTest {
    @Autowired ReviewController reviewController;

    // Test reviews
    //private final Review EXISTING_TEST_REVIEW_1 = new Review(1, "Very powerful", 5, true, 1, 2, LocalDateTime.parse("2008-11-11T14:34:23"));
    //private final Review EXISTING_TEST_REVIEW_2 = new Review(2, "Nice guy", 4, false, 1, 2, LocalDateTime.parse("2022-01-04T11:54:22"));
    //private final Review EXISTING_TEST_REVIEW_3 = new Review(3, "Not sure how to feel about this guy", 1, true, 2, 1, LocalDateTime.parse("2017-10-03T03:56:11"));
    private final Review TEST_REVIEW = new Review(4, "The best tester", 5, false, 2, 1, LocalDateTime.of(2022, 5, 15, 13, 46, 23));
    private final Review INVALID_TEST_REVIEW = new Review(5, "The best tester", 6, false, 2, 1, LocalDateTime.of(2022, 5, 15, 13, 46, 23));
    //private final Review INVALID_TEST_REVIEW_2 = new Review(5, null, 5, false, 2, 1, null);

    @Test
    @Order(1)
    public void getAmountOfReviews_correctAmount_true() {
        assertEquals(2, reviewController.getAmountOfReviews(2).getBody());
    }

    @Test
    @Order(2)
    public void getAmountOfReviews_notCorrectAmount_false() {
        assertNotEquals(1, reviewController.getAmountOfReviews(2).getBody());
    }

    @Test
    @Order(3)
    public void getAverageScore_correntAverage_true() {
        assertEquals(4.50, reviewController.getAverageScore(2).getBody());
    }

    @Test
    @Order(4)
    public void getAverageScore_notCorrentAverage_false() {
        assertNotEquals(3.68, reviewController.getAverageScore(1).getBody());
    }

    @Test
    @Order(5)
    public void getReviewList_success_true_author() {
        List<Review> list = Arrays.asList(new Review(3, "Not sure how to feel about this guy", 1, true, 2, 1, LocalDateTime.of(2017, Month.OCTOBER, 3, 3, 56, 11)));
        assertEquals(list.toString(), reviewController.getReviewList(null, 2).getBody().toString());
    }

    @Test
    @Order(6)
    public void getReviewList_success_true_subject() {
        List<Review> list = Arrays.asList(new Review(3, "Not sure how to feel about this guy", 1, true, 2, 1, LocalDateTime.of(2017, Month.OCTOBER, 3, 3, 56, 11)));
        assertEquals(list.toString(), reviewController.getReviewList(1, null).getBody().toString());
    }

    @Test
    @Order(7)
    public void getReviewList_success_true_author_subject() {
        List<Review> list = Arrays.asList(new Review(3, "Not sure how to feel about this guy", 1, true, 2, 1, LocalDateTime.of(2017, Month.OCTOBER, 3, 3, 56, 11)));
        assertEquals(list.toString(), reviewController.getReviewList(1, 2).getBody().toString());
    }

    @Test
    @Order(8)
    public void getAverageScoreAsOwner_success_true() {
        assertEquals(5.0, reviewController.getAverageScoreAsOwner(2).getBody());
    }

    @Test
    @Order(9)
    public void getAverageScoreAsRenter_success_true() {
        assertEquals(4.0, reviewController.getAverageScoreAsRenter(2).getBody());
    }

    @Test
    @Order(10)
    public void registerNewReview_success_true() {
        assertEquals(201, reviewController.registerNewReview(TEST_REVIEW).getStatusCodeValue());
    }


    @Test
    @Order(11)
    public void registerNewReview_success_false_conflict() {
        assertEquals(409, reviewController.registerNewReview(INVALID_TEST_REVIEW).getStatusCodeValue());
    }

    @Test
    @Order(12)
    public void getById_isSame_true() {
        assertEquals(200, reviewController.getById(2).getStatusCodeValue());
    }

    @Test
    @Order(13)
    public void getReviewList_isExpected_true() {
        assertTrue(reviewController.getReviewList(null, null).getBody().size() == 4);
    }
    
}

package ntnu.idatt.boco.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

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

    @Test
    @Order(1)
    public void registerNewReview_success_true() {
        assertEquals(201, reviewController.registerNewReview(TEST_REVIEW).getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void getById_isSame_true() {
        assertEquals(200, reviewController.getById(2).getStatusCodeValue());
    }

    @Test
    @Order(3)
    public void getReviewList_isExpected_true() {
        assertTrue(reviewController.getReviewList(null, null).getBody().size() == 4);
    }
    
}

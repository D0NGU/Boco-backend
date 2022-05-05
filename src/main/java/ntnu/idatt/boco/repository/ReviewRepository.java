package ntnu.idatt.boco.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ntnu.idatt.boco.model.Review;

/**
 * This class is responsible for communication with the database regarding {@link Review}.
 */
@Repository
public class ReviewRepository {
    @Autowired private JdbcTemplate jdbcTemplate;

    /**
     * Method to add a new review to the database
     * @param review review to add
     * @return the amount of rows affected (1 if added one review)
     */
    public int newReview(Review review) {
        return jdbcTemplate.update("INSERT INTO reviews(text, stars, owner, author, subject, date) VALUES (?,?,?,?,?,?)",
                new Object[]{review.getText(), review.getStars(), review.isOwner(), review.getAuthor(), review.getSubject(), LocalDateTime.now()});
    }

    // Methods for getting reviews
    // TODO - Pagination when getting reviews?
    public List<Review> getAllReviews() {
        String sql = "SELECT * FROM reviews ORDER BY date DESC;";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Review.class));
    }
    public List<Review> getAllReviewsBySubject(int subject) {
        String sql = "SELECT * FROM reviews WHERE subject="+subject+" ORDER BY date DESC;";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Review.class));
    }
    public List<Review> getAllReviewsByAuthor(int author) {
        String sql = "SELECT * FROM reviews WHERE author="+author+" ORDER BY date DESC;";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Review.class));
    }
    public List<Review> getReviewsByAuthorSubject(int author, int subject) {
        String sql = "SELECT * FROM reviews WHERE author="+author+" AND subject="+subject+" ORDER BY date DESC;";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Review.class));
    }
    public Review getReview(int reviewId) {
        String sql = "SELECT * FROM reviews WHERE review_id = ?;";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Review.class), reviewId);
    }

    /**
     * Gets amount of reviews written by a spesific user
     * @param userId the user
     * @return the amount of reviews written
     */
    public int getAmountOfAuthorReviews(int userId) {
        String sql = "SELECT COUNT(review_id) AS NumberOfReviews FROM reviews WHERE author=?;";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

    /**
     * Gets amount of reviews written about a spesific user
     * @param userId the user
     * @return the amount of reviews written
     */
    public int getAmountOfSubjectReviews(int userId) {
        String sql = "SELECT COUNT(review_id) AS NumberOfReviews FROM reviews WHERE subject=?;";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

    /**
     * Gets the average review-score for a spesific user
     * @param userId the user
     * @return the average score (a decimal number between 1 and 5)
     */
    public Double getAverageUserReviews(int userId) {
        String sql = "SELECT CAST(AVG(stars+0.0) AS DECIMAL(10,3)) FROM reviews WHERE subject=?;";
        return jdbcTemplate.queryForObject(sql, Double.class, userId);
    }

    /**
     * Gets the average review-score for a spesific user as owner
     * @param userId the user
     * @return the average score (a decimal number between 1 and 5)
     */
    public Double getAverageUserReviewsAsOwner(int userId) {
        String sql = "SELECT CAST(AVG(stars+0.0) AS DECIMAL(10,3)) FROM reviews WHERE subject=? AND owner = true;";
        return jdbcTemplate.queryForObject(sql, Double.class, userId);
    }

    /**
     * Gets the average review-score for a spesific user as renter
     * @param userId the user
     * @return the average score (a decimal number between 1 and 5)
     */
    public Double getAverageUserReviewsAsRenter(int userId) {
        String sql = "SELECT CAST(AVG(stars+0.0) AS DECIMAL(10,3)) FROM reviews WHERE subject=? AND owner = false;";
        return jdbcTemplate.queryForObject(sql, Double.class, userId);
    }
}

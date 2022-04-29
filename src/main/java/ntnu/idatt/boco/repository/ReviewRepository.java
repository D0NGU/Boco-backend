package ntnu.idatt.boco.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ntnu.idatt.boco.model.Review;

@Repository
public class ReviewRepository {
    @Autowired private JdbcTemplate jdbcTemplate;

    public List<Review> getAllReviews() {
        String sql = "SELECT * FROM reviews ORDER BY date;";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Review.class));
    }

    public List<Review> getAllReviewsBySubject(int subject) {
        String sql = "SELECT * FROM reviews WHERE subject="+subject+" ORDER BY date;";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Review.class));
    }

    public List<Review> getAllReviewsByAuthor(int author) {
        String sql = "SELECT * FROM reviews WHERE author="+author+" ORDER BY date;";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Review.class));
    }

    public List<Review> getReviewsByAuthorSubject(int author, int subject) {
        String sql = "SELECT * FROM reviews WHERE author="+author+" AND subject="+subject+" ORDER BY date;";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Review.class));
    }

    public Review getReview(int reviewId) {
        String sql = "SELECT * FROM reviews WHERE review_id = ?;";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Review.class), reviewId);
    }

    /**
     * Method to add a new review to the database
     * @param review review to add
     * @return the amount of rows affected (1 if added one review)
     */
    public int newReview(Review review) {
        return jdbcTemplate.update("INSERT INTO reviews(text, stars, owner, author, subject, date) VALUES (?,?,?,?,?,?)",
                new Object[]{review.getText(), review.getStars(), review.isOwner(), review.getAuthor(), review.getSubject(), LocalDateTime.now()});
    }
    
}

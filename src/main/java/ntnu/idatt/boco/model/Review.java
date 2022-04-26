package ntnu.idatt.boco.model;

/**
 * This class represents a user review.
 */
public class Review {
    private int reviewId;
    private String text;
    private int stars;
    private boolean owner;
    private int author;
    private int subject;

    public Review () {}

    /**
     * Constructor for a review object.
     * @param reviewId the unique id of the review
     * @param text the review itself
     * @param stars a number of stars used as a rating
     * @param owner true if the subject of the review is the user renting out a product
     * @param author the user that wrote the review
     * @param subject the user the review is targeted at
     */
    public Review(int reviewId, String text, int stars, boolean owner, int author, int subject) {
        this.reviewId = reviewId;
        this.text = text;
        this.stars = stars;
        this.owner = owner;
        this.author = author;
        this.subject = subject;
    }

    public int getReviewId() {
        return reviewId;
    }
    public String getText() {
        return text;
    }
    public int getStars() {
        return stars;
    }
    public boolean isOwner() {
        return owner;
    }
    public int getAuthor() {
        return author;
    }
    public int getSubject() {
        return subject;
    }

    public void setText(String text) {
        this.text = text;
    }
    public void setStars(int stars) {
        if (stars>=1 && stars<=5) {
            this.stars = stars;
        }
    }
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
    public void setOwner(boolean owner) {
        this.owner = owner;
    }
    public void setAuthor(int author) {
        this.author = author;
    }
    public void setSubject(int subject) {
        this.subject = subject;
    }
}

package ntnu.idatt.boco.model;

import java.time.LocalDate;

/**
 * This class represents a rental request or rental agreement.
 */
public class Rental {
    private int rentalId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private boolean accepted;
    private int productId;
    private int userId;

    public Rental() {}

    /**
     * Constructor for a rental object.
     * @param rentalId the unique id of the rental request/agreement
     * @param dateFrom the date the rental starts
     * @param dateTo the date the rental ends
     * @param productId the id of the product being rented
     * @param userId the id of the user renting the product
     */
    public Rental(int rentalId, LocalDate dateFrom, LocalDate dateTo, boolean accepted, int productId, int userId) {
        this.rentalId = rentalId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.accepted = accepted;
        this.productId = productId;
        this.userId = userId;
    }

    public int getRentalId() { 
        return rentalId; 
    }
    public LocalDate getDateFrom() { 
        return dateFrom; 
    }
    public LocalDate getDateTo() { 
        return dateTo; 
    }
    public boolean isAccepted() { 
        return accepted; 
    }
    public int getProductId() { 
        return productId; 
    }
    public int getUserId() { 
        return userId; 
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }
    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "rentalId=" + rentalId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", accepted=" + accepted +
                ", productId=" + productId +
                ", userId=" + userId +
                '}';
    }
}

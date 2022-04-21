package ntnu.idatt.boco.model;

import java.sql.Date;

public class Rental {
    private int rentalId;
    private Date dateFrom;
    private Date dateTo;
    private int productId;
    private int userId;

    public Rental() {}
    public Rental(int rentalId, Date dateFrom, Date dateTo, int productId, int userId) {
        this.rentalId = rentalId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.productId = productId;
        this.userId = userId;
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

    public int getRentalId() { return rentalId; }

    public Date getDateFrom() { return dateFrom; }

    public Date getDateTo() { return dateTo; }

    public int getProductId() { return productId; }

    public int getUserId() { return userId; }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}

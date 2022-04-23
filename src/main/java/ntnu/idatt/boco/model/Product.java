package ntnu.idatt.boco.model;

import java.time.LocalDate;

public class Product {
    private int productId;
    private String title;
    private String description;
    private String address;
    private Double price;
    private boolean unlisted;
    private LocalDate availableFrom;
    private LocalDate availableTo;
    private int userId;
    private String category;

    public Product () {}

    public Product(int productId, String title, String description, String address, Double price, boolean unlisted, LocalDate availableFrom, LocalDate availableTo, int userId, String category) {
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.address = address;
        this.price = price;
        this.unlisted = unlisted;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
        this.userId = userId;
        this.category = category;
    }

    public Product(String description, String address, Double price, boolean unlisted, LocalDate availableFrom, LocalDate availableTo, String category) {
        this.description = description;
        this.address = address;
        this.price = price;
        this.unlisted = unlisted;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
        this.category = category;
    }

    public int getProductId() {
        return productId;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getAddress() {
        return address;
    }
    public Double getPrice() {
        return price;
    }
    public boolean isUnlisted() {
        return unlisted;
    }
    public int getUserId() {
        return userId;
    }
    public String getCategory() {
        return category;
    }
    public LocalDate getAvailableFrom() {
        return availableFrom;
    }
    public LocalDate getAvailableTo() {
        return availableTo;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public void setUnlisted(boolean unlisted) {
        this.unlisted = unlisted;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
    }
    public void setAvailableTo(LocalDate availableTo) {
        this.availableTo = availableTo;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", unlisted=" + unlisted +
                ", userId=" + userId +
                ", category='" + category + '\'' +
                '}';
    }
}

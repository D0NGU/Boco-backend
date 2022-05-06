package ntnu.idatt.boco.model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class represents a product.
 */
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
    private ArrayList<ProductImage> images;
    private String tlf;

    public Product () {}

    public Product(int productId, String title, String description, String address, Double price, boolean unlisted, LocalDate availableFrom, LocalDate availableTo, int userId, String category, ArrayList<ProductImage> images, String tlf) {
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
        this.images = images;
        this.tlf = tlf;
    }

    /**
     * Constructor for a product object.
     * @param productId the unique id of the product
     * @param title the title of the product
     * @param description a description of the product
     * @param address the address where the product is to be picked up
     * @param price the price of renting the product
     * @param unlisted true if the product is unlisted, false otherwise
     * @param availableFrom the date the product is available to rent from
     * @param availableTo the date the product is available to rent to
     * @param userId the id of the user who owns the product
     * @param category the category of the product
     */
    public Product(int productId, String title, String description, String address, Double price, boolean unlisted, LocalDate availableFrom, LocalDate availableTo, int userId, String category, String tlf) {
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
        this.tlf = tlf;
    }

    /**
     * Constructor for a rental object without needing id, title and userId.
     * @param description a description of the product
     * @param address the address where the product is to be picked up
     * @param price the price of renting the product
     * @param unlisted true if the product is unlisted, false otherwise
     * @param availableFrom the date the product is available to rent from
     * @param availableTo the date the product is available to rent to
     * @param category the category of the product
     */
    public Product(String description, String address, Double price, boolean unlisted, LocalDate availableFrom, LocalDate availableTo, String category, String tlf) {
        this.description = description;
        this.address = address;
        this.price = price;
        this.unlisted = unlisted;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
        this.category = category;
        this.images = new ArrayList<ProductImage>();
        this.tlf = tlf;
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
    public ArrayList<ProductImage> getImages() {
        return images;
    }
    public String getTlf() {
        return tlf;
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
    public void setImages(ArrayList<ProductImage> images) {
        this.images = images;
    }
    public void setTlf(String tlf) {
        this.tlf = tlf;
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
                ", date_from=" + availableFrom +
                ", date_to=" + availableTo +
                '}';
    }
}

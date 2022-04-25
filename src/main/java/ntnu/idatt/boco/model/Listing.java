package ntnu.idatt.boco.model;

import java.time.LocalDate;
import java.util.List;

public class Listing {
    Product product;
    User owner;
    List<AvailabilityWindow> availabilityWindows;
    List<Category> categories;
    List<ProductImage> images;

    public Listing() {
    }

    public Listing(Product product, User owner, List<AvailabilityWindow> availabilityWindows, List<Category> categories, List<ProductImage> images) {
        this.product = product;
        this.owner = owner;
        this.availabilityWindows = availabilityWindows;
        this.categories = categories;
        this.images = images;
    }

    public Product getProduct() {
        return product;
    }

    public User getOwner() {
        return owner;
    }

    public List<AvailabilityWindow> getAvailabilityWindows() {
        return availabilityWindows;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setAvailabilityWindows(List<AvailabilityWindow> availabilityWindows) {
        this.availabilityWindows = availabilityWindows;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }
}

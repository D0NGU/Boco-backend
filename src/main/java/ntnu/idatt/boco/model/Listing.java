package ntnu.idatt.boco.model;

import java.util.List;

/**
 * This class contains all surrounding information about any {@link Product}.
 * This information contains properties of the product, any images, owner info, categories/sub-categories and availability.
 */
public class Listing {
    Product product;
    User owner;
    List<AvailabilityWindow> availabilityWindows;
    List<Category> categories;
    List<ProductImage> images;

    public Listing() {}

    /**
     * Constructor for a listing object.
     * @param product the product that is available for renting
     * @param owner the user that owns the product
     * @param availabilityWindows a list of all the available time slots to rent the product
     * @param categories a list of all the products categories
     * @param images a list of all the product images
     */
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

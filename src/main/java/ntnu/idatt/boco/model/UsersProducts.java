package ntnu.idatt.boco.model;

import java.util.List;

/**
 * This class represents all the products owned by a user.
 * It contains information about the {@link User}, its {@link Product} and the {@link ProductImage}
 */
public class UsersProducts {
    User user;
    List<Product> products;
    List<ProductImage> images;

    public UsersProducts() {}

    /**
     * Constructor for a UsersProducts object
     * @param user a user that owns products
     * @param products a list of all the products the user owns
     * @param images a list of all the images of the products
     */
    public UsersProducts(User user, List<Product> products, List<ProductImage> images) {
        this.user = user;
        this.products = products;
        this.images = images;
    }

    public User getUser() {
        return user;
    }
    public List<Product> getProducts() {
        return products;
    }
    public List<ProductImage> getImages() {
        return images;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public void setImages(List<ProductImage> images) {
        this.images = images;
    }
}

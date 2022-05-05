package ntnu.idatt.boco.model;

import java.util.List;

/**
 * This class represents all the products owned by a user.
 * It contains information about the {@link User}, its {@link Product} and the {@link ProductImage}
 */
public class UsersProducts {
    User user;
    List<Product> products;

    public UsersProducts() {}

    /**
     * Constructor for a UsersProducts object
     * @param user a user that owns products
     * @param products a list of all the products the user owns
     */
    public UsersProducts(User user, List<Product> products) {
        this.user = user;
        this.products = products;
    }

    public User getUser() {
        return user;
    }
    public List<Product> getProducts() {
        return products;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

package ntnu.idatt.boco.model;

import java.util.List;

public class UsersProducts {
    User user;
    List<Product> products;
    List<ProductImage> images;

    public UsersProducts() {}

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

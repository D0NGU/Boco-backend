package ntnu.idatt.boco.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private String address;
    private Double price;
    private boolean unlisted;
    private int userId;
    private String category;

    public Product () {
    }

    public Product(int id, String name, String description, String address, Double price, boolean unlisted, int userId, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.price = price;
        this.unlisted = unlisted;
        this.userId = userId;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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
}

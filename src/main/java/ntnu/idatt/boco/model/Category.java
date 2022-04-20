package ntnu.idatt.boco.model;

public class Category {
    private String category;
    private String mainCategory;

    public Category() {}
    public Category(String category, String mainCategory) {
        this.category = category;
        this.mainCategory = mainCategory;
    }

    public String getCategory() {
        return category;
    }

    public String getMainCategory() {
        return mainCategory;
    }
}

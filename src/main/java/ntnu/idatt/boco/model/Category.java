package ntnu.idatt.boco.model;

import java.io.Serializable;

public class Category implements Serializable {
    private String category;
    private String mainCategory;

    public Category() {}
    public Category(String category, String mainCategory) {
        this.category = category;
        this.mainCategory = mainCategory;
    }
    public Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }
}

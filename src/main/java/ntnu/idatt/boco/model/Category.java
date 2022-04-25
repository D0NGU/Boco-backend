package ntnu.idatt.boco.model;


import java.util.Objects;

/**
 * This class represents a category
 */
public class Category {
    private String category;
    private String mainCategory;

    public Category() {}

    /**
     * Constructor for a category object
     * @param category the sub-category
     * @param mainCategory the main category
     */
    public Category(String category, String mainCategory) {
        this.category = category;
        this.mainCategory = mainCategory;
    }

    /**
     * Constructor for a category object without needing a main category
     * @param category the sub-category
     */
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

    @Override
    public String toString() {
        return "Category{" +
                "category='" + category + '\'' +
                ", mainCategory='" + mainCategory + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category1 = (Category) o;
        return Objects.equals(category, category1.category) && Objects.equals(mainCategory, category1.mainCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, mainCategory);
    }
}

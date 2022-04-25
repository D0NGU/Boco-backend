package ntnu.idatt.boco.model;

/**
 * This class represents a category.
 */
public class Category {
    private String category;
    private String mainCategory;

    public Category() {}

    /**
     * Constructor for a category object.
     * @param category the sub category
     * @param mainCategory the main category
     */
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

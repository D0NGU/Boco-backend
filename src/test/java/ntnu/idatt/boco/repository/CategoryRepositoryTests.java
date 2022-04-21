package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository repository;

    @Test
    void getSubCategoryTest() {
        List<Category> subCategories = repository.getSubCategories("verktøy", repository.getAll());
        for (Category category : subCategories) {
            System.out.println("Found "+category.getCategory());
        }
    }
}

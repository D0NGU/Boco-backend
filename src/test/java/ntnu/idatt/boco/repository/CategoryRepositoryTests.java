package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository repository;

    @Test
    void getSubCategoryTest() {
        List<Category> expected = new ArrayList<>();
        expected.add(new Category("verktøy", null));
        expected.add(new Category("hammere", "verktøy"));
        expected.add(new Category("skrutrekkere", "verktøy"));
        expected.add(new Category("stjerneskrutrekkere", "skrutrekkere"));



        List<Category> allCategories = new ArrayList<>(expected);

        allCategories.add(new Category("sporstutsyr", null));
        allCategories.add(new Category("slalom", "sporstutsyr"));


        List<Category> subCategories = repository.getSubCategories("verktøy", allCategories);

        assertEquals(expected, subCategories);
    }
}

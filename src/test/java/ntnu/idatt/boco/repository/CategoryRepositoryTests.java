package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.model.Category;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test") // To use 'application-test.properties', 'schema.sql' and 'data-test.sql' to define H2 database
@SpringBootTest
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository repository;

    List<Category> allCategories;
    @Test
    @Order(1)
    void getSubCategoryTest() {
        List<Category> expected = new ArrayList<>();
        expected.add(new Category("verktøy", null));
        expected.add(new Category("hammere", "verktøy"));
        expected.add(new Category("skrutrekkere", "verktøy"));
        expected.add(new Category("stjerneskrutrekkere", "skrutrekkere"));



        allCategories = new ArrayList<>(expected);

        allCategories.add(new Category("sporstutsyr", null));
        allCategories.add(new Category("slalom", "sporstutsyr"));


        List<Category> subCategories = repository.getSubCategories("verktøy", allCategories);

        assertEquals(expected, subCategories);
    }

    @Test
    @Order(2)
    void getMainCategoies() {
        List<Category> expected = new ArrayList<>();
        expected.add(new Category("stjerneskrutrekkere", "skrutrekkere"));
        expected.add(new Category("skrutrekkere", "verktøy"));
        expected.add(new Category("verktøy", null));

        assertArrayEquals(expected.toArray(), repository.getMainCategories("stjerneskrutrekkere").toArray());
    }
}

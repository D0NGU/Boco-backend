package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Category> getAll() {
        return jdbcTemplate.query("SELECT * FROM categories", BeanPropertyRowMapper.newInstance(Category.class));
    }

    public List<Category> getSubCategories(String main) {
        List<Category> subs = new ArrayList<>();
        List<Category> categories = getAll();

        for (Category category : categories) {
            if (category.getMainCategory().equals(main)) {
                subs.addAll(getSubCategories(category.getCategory()));
            }
        }
        return subs;
    }
}

package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class CategoryRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Category> getAll() {
        return jdbcTemplate.query("SELECT * FROM categories", BeanPropertyRowMapper.newInstance(Category.class));
    }
    public Category getCategory(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM categories WHERE CATEGORY = ?", BeanPropertyRowMapper.newInstance(Category.class), name);
    }

    /**
     * Method for finding all sub categories of a given category
     * @param main the name of the main category
     * @param categories list of categories to filter through
     * @return all the sub categories
     */
    public List<Category> getSubCategories(String main, List<Category> categories) {
        List<Category> subs = new ArrayList<>();

        Iterator<Category> it = categories.iterator();

        while (it.hasNext()) {
            Category cat = it.next();
            if (cat.getCategory().equals(main)){
                subs.add(cat);
                it.remove();
            }
            if (cat.getMainCategory()!=null && cat.getMainCategory().equals(main)) {
                List<Category> c = new ArrayList<>(categories);
                subs.addAll(getSubCategories(cat.getCategory(), c));
            }
        }
        return subs;
    }

    public List<Category> getMainCategories(String sub) {
        Category category = getCategory(sub);
        List <Category> all = getAll();
        List<Category> mains = new ArrayList<>();

        while (category.getMainCategory() != null) {
            mains.add(category);
            category = getCategory(category.getMainCategory());
        }
        mains.add(category);
        return mains;
    }
}

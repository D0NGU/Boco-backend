package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.Category;
import ntnu.idatt.boco.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/categories")
public class CategoryController {
    Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    CategoryRepository categoryRepository;

    /**
     * Method for retrieving all categories
     * @return an HTTP response containing a list of all categories and an HTTP status code
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        logger.info("GET-request for all categories.");
        try {
            List<Category> categoryList = categoryRepository.getAll();
            if (categoryList.isEmpty()) {
                logger.info("No categories found.");
                return new ResponseEntity<>(categoryList, HttpStatus.OK);
            } else {
                logger.info("Success - all categories retrieved.");
                return new ResponseEntity<>(categoryList, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Retrieval of all categories failed.");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

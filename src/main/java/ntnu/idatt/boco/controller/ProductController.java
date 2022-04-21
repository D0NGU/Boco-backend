package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.Product;
import ntnu.idatt.boco.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/products")
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductRepository repository;

    @GetMapping("/")
    @ResponseBody
    public List<Product> getAll() {
        return repository.getAll();
    }

    @GetMapping("/category/{category}")
    @ResponseBody
    public List<Product> getByCategory(@PathVariable String category) {
        return repository.getFromCategory(category);
    }

    @GetMapping("/product/{id}")
    @ResponseBody
    public Product getById(@PathVariable String id) {
        return repository.getProduct(id);
    }
}

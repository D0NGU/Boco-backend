package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.AvailabilityWindow;
import ntnu.idatt.boco.model.Product;
import ntnu.idatt.boco.model.Rental;
import ntnu.idatt.boco.repository.ProductRepository;
import ntnu.idatt.boco.repository.RentalRepository;
import ntnu.idatt.boco.service.ProductService;
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
    ProductRepository productRepository;

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    ProductService service;

    @PostMapping("/new")
    public void newProduct(@RequestBody Product product) {
        logger.info("Saving new product: " +product.getName());
        productRepository.newProduct(product);
    }

    @PostMapping("/edit/{id}")
    public void editProduct(@PathVariable String id,@RequestBody Product product) {
        logger.info("Editing product: " +id);
        productRepository.editProduct(product, id);
    }


    @GetMapping("/")
    @ResponseBody
    public List<Product> getAll() {
        return productRepository.getAll();
    }

    @GetMapping("/category/{category}")
    @ResponseBody
    public List<Product> getByCategory(@PathVariable String category) {
        return productRepository.getFromCategory(category);
    }

    @GetMapping("/product/{id}")
    @ResponseBody
    public Product getById(@PathVariable String id) {
        return productRepository.getProduct(id);
    }

    @GetMapping("/product/{id}/availability")
    @ResponseBody
    public List<AvailabilityWindow> getAvailability(@PathVariable String id) {
        Product product = productRepository.getProduct(id);
        List<Rental> rentals = rentalRepository.getRentals(id);

        return service.getAvailability(product, rentals);
    }
}

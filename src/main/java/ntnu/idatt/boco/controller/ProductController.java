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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/products")
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired ProductRepository productRepository;
    @Autowired RentalRepository rentalRepository;
    @Autowired ProductService service;

    @PostMapping("/create")
    public ResponseEntity<String> newProduct(@RequestBody Product product) {
        logger.info("Creating new product: " + product.getName());
        productRepository.newProduct(product);
        try {
            productRepository.newProduct(product);
            logger.info("Product created");
            return new ResponseEntity<>("Created successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info("Error creating new product");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<String> editProduct(@PathVariable String id,@RequestBody Product product) {
        logger.info("Editing product: " + id);
        try {
            productRepository.editProduct(product, id);
            logger.info("Editing product: " + id);
            return new ResponseEntity<>("Created successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info("Error editing product");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Product> getById(@PathVariable String id) {
        logger.info("Getting product " + id);
        try {
            return new ResponseEntity<>(productRepository.getProduct(id), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Error getting product");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<List<Product>> getAll() {
        // TODO Trenger kanskje ikke å hente alle produkt i hele databasen? 10 nyeste? Søkefunksjon i backend?
        logger.info("Getting list of products");
        try {
            return new ResponseEntity<>(productRepository.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Error getting list of products");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{category}")
    @ResponseBody
    public ResponseEntity<List<Product>> getByCategory(@PathVariable String category) {
        logger.info("Getting all products in " + category);
        try {
            return new ResponseEntity<>(productRepository.getFromCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Error getting list of products by category");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{id}/availability")
    @ResponseBody
    public List<AvailabilityWindow> getAvailability(@PathVariable String id) {
        Product product = productRepository.getProduct(id);
        List<Rental> rentals = rentalRepository.getRentals(id);

        return service.getAvailability(product, rentals);
    }
}

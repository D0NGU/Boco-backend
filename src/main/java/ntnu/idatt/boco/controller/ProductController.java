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

    @PostMapping("/product/create")
    public ResponseEntity<String> newProduct(@RequestBody Product product) {
        logger.info("Creating new product: " + product.getName());
        //productRepository.newProduct(product);
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

    @PostMapping("/edit/{productId}")
    public ResponseEntity<String> editProduct(@PathVariable String productId,@RequestBody Product product) {
        logger.info("Editing product: " + productId);
        try {
            productRepository.editProduct(product, productId);
            logger.info("Editing product: " + productId);
            return new ResponseEntity<>("Created successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info("Error editing product");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{productId}")
    @ResponseBody
    public ResponseEntity<Product> getById(@PathVariable String productId) {
        logger.info("Getting product " + productId);
        try {
            return new ResponseEntity<>(productRepository.getProduct(productId), HttpStatus.OK);
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

    @GetMapping("/{category}")
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

    @GetMapping("/product/{productId}/availability")
    @ResponseBody
    public ResponseEntity<List<AvailabilityWindow>> getAvailability(@PathVariable String productId) {
        logger.info("Request for availability window for product " + productId);
        try {
            Product product = productRepository.getProduct(productId);
            List<Rental> rentals = rentalRepository.getRentals(productId);
            return new ResponseEntity<>(service.getAvailability(product, rentals), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Could not get availability for product " + productId);
            logger.error("Error: " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    @ResponseBody
    public List<Product> getProductFromSearch(@RequestParam String q) {
        return productRepository.searchProductByWord(q);
    }
}

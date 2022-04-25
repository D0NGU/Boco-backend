package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.*;
import ntnu.idatt.boco.repository.CategoryRepository;
import ntnu.idatt.boco.repository.ImageRepository;
import ntnu.idatt.boco.repository.ProductRepository;
import ntnu.idatt.boco.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Listing controller provides an endpoint for generating listings for a product.
 * A listing is a model supposed to contain all information necessary for creating a view of the product.
 * This information contains title of the product, images, owner info, category path and availability.
 */
@CrossOrigin
@RestController
@RequestMapping("api/listing")
public class ListingController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired ProductRepository productRepository;
    @Autowired UserRepository userRepository;
    @Autowired ProductController productController;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ImageRepository imageRepository;


    /**
     * The get method to get a listing based on a product id
     * @param productId the id the listing is created for
     * @return A listing model and an Http status
     */
    @GetMapping("/{productId}")
    @ResponseBody
    public ResponseEntity<Listing> getListingById(@PathVariable int productId) {
        logger.info("Getting listing for product " +  productId);
        try {
            Product product = productRepository.getProduct(productId);
            logger.info("Found product");
            User user = userRepository.getUserById(product.getUserId());
            logger.info("Found user");
            List<AvailabilityWindow> availability = productController.getAvailability(productId).getBody();
            logger.info("Found availability");
            List<Category> categories = categoryRepository.getMainCategories(product.getCategory());
            logger.info("Found categories");
            List<ProductImage> images = imageRepository.getImagesByProductId(productId);
            logger.info("Found all attributes");
            return new ResponseEntity<>(new Listing(product, user, availability, categories, images), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Error getting product");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package ntnu.idatt.boco.controller;

import lombok.NoArgsConstructor;
import ntnu.idatt.boco.model.*;
import ntnu.idatt.boco.repository.CategoryRepository;
import ntnu.idatt.boco.repository.ImageRepository;
import ntnu.idatt.boco.repository.ProductRepository;
import ntnu.idatt.boco.repository.UserRepository;
import ntnu.idatt.boco.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Listing controller provides an endpoint for generating listings for a product.
 * @see ntnu.idatt.boco.model.Listing Listing
 */
@CrossOrigin
@RestController
@RequestMapping("api/listing")
@NoArgsConstructor
public class ListingController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired ProductRepository productRepository;
    @Autowired ProductController productController;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ImageRepository imageRepository;
    @Autowired UserRepository userService;

    /**
     * The get method to get a listing based on a productId
     * @param productId the ID the listing is created for
     * @return status code and a listing object:
     *          {@code 200} if success,
     *          {@code 500} if error
     */
    @GetMapping("/{productId}")
    @ResponseBody
    public ResponseEntity<Listing> getListingById(@PathVariable int productId) {
        logger.info(productId + ": Creating listing");
        try {
            Product product = productRepository.getProduct(productId);
            User user = userService.getUserById(product.getUserId());

            List<AvailabilityWindow> availability = productController.getAvailability(productId).getBody();
            List<Category> categories = categoryRepository.getMainCategories(product.getCategory());
            List<ProductImage> images = imageRepository.getImagesByProductId(productId);
            return new ResponseEntity<>(new Listing(product, user, availability, categories, images), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Could not create listing for " + productId);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

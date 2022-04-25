package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.AvailabilityWindow;
import ntnu.idatt.boco.model.Listing;
import ntnu.idatt.boco.model.Product;
import ntnu.idatt.boco.model.User;
import ntnu.idatt.boco.repository.ProductRepository;
import ntnu.idatt.boco.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/listing")
public class ListingController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired ProductRepository productRepository;
    @Autowired UserRepository userRepository;
    @Autowired ProductController productController;



    /*@GetMapping("/{productId}")
    @ResponseBody
    public ResponseEntity<Listing> getListingById(@PathVariable int productId) {
        logger.info("Getting listing for product " +  productId);
        try {
            Product product = productRepository.getProduct(productId);
            User user = userRepository.getUserById(product.getUserId());
            List<AvailabilityWindow> availability = productController.getAvailability(productId).getBody();
        } catch (Exception e) {
            logger.info("Error getting product");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}

package ntnu.idatt.boco.controller;

import lombok.NoArgsConstructor;
import ntnu.idatt.boco.model.*;
import ntnu.idatt.boco.repository.ImageRepository;
import ntnu.idatt.boco.repository.ProductRepository;
import ntnu.idatt.boco.repository.RentalRepository;
import ntnu.idatt.boco.service.ProductService;
import ntnu.idatt.boco.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class contains methods responsible for handling HTTP requests regarding products.
 */
@CrossOrigin
@RestController
@RequestMapping("api/products")
@NoArgsConstructor
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired ProductRepository productRepository;
    @Autowired RentalRepository rentalRepository;
    @Autowired ImageRepository imageRepository;
    @Autowired ProductService service;
    @Autowired UserService userService;

    /**
     * Method for handling POST-requests for registering a new product
     * @param product the product to be registered
     * @return an HTTP response containing a result message as a String and a HTTP status code
     */
    @PostMapping
    public ResponseEntity<String> newProduct(@RequestBody Product product) {
        logger.info("Creating new product: " + product.getTitle());
        try {
            productRepository.newProduct(product);
            logger.info("Product created");
            return new ResponseEntity<>("Created successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating new product");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling PUT-requests for editing a product
     * @param productId the id of the product
     * @param product   the edited product
     * @return an HTTP response containing a result message as a String and an HTTP status code
     */
    @PutMapping("/{productId}")
    public ResponseEntity<String> editProduct(@PathVariable int productId,@RequestBody Product product) {
        logger.info("Editing product: " + productId);
        try {
            productRepository.editProduct(product, productId);
            logger.info("Editing product: " + productId);
            return new ResponseEntity<>("Created successfully!", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error editing product");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling GET-requests for retrieving a product by id
     * @param productId the id of the product
     * @return an HTTP response containing the retrieved product and an HTTP status code
     */
    @GetMapping("/{productId}")
    @ResponseBody
    public ResponseEntity<Product> getById(@PathVariable int productId) {
        logger.info("Getting product " + productId);
        try {
            return new ResponseEntity<>(productRepository.getProduct(productId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting product");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling GET-requests for retrieving a products availability window
     * @param productId the id of the product
     * @return an HTTP response containing a list of the availability windows of the product and an HTTP status code
     */
    @GetMapping("/{productId}/availability")
    @ResponseBody
    public ResponseEntity<List<AvailabilityWindow>> getAvailability(@PathVariable int productId) {
        logger.info("Request for availability window for product " + productId);
        try {
            Product product = productRepository.getProduct(productId);
            List<Rental> rentals = rentalRepository.getAcceptedRentals(productId, true);
            return new ResponseEntity<>(service.getAvailability(product, rentals), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Could not get availability for product " + productId);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling POST-request for adding new images
     * @param productId the id of the product to add the image to
     * @param image     the image to be added
     * @return an HTTP response containing a result message as a String and an HTTP status code
     */
    @PostMapping("/{productId}/image")
    public ResponseEntity<String> newImage(@PathVariable int productId, @RequestBody ProductImage image) {
        logger.info("Adding picture: " + image.getImgName() + " to product " + productId);
        try {
            imageRepository.newPicture(image);
            logger.info("Image saved");
            return new ResponseEntity<>("Created successfully!", HttpStatus.CREATED);
        }catch (Exception e) {
            logger.error("Error saving new product");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling GET-requests for retrieving a products images
     * @param productId the id of the product
     * @return an HTTP response containing a list of product images and an HTTP status code
     */
    @GetMapping("/{productId}/image")
    @ResponseBody
    public ResponseEntity<List<ProductImage>> getImagesByProudctId(@PathVariable int productId) {
        logger.info("Finding images by product id: " + productId);
        try {
            List<ProductImage> images = imageRepository.getImagesByProductId(productId);
            logger.info(images.size() + " images found");
            return new ResponseEntity<>(images, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting images");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Method for handling GET-requests for searching for products
     * @param q the word to search for
     * @param page the page the user is getting redirected to
     * @return a list of all the products matching the search-word
     */
    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<Product>> getProductFromSearch(@RequestParam(required = false) String q, @RequestParam(required = false) String category, @RequestParam int page, @RequestParam String sortBy,
                                                              @RequestParam boolean ascending) {
        logger.info("Request for a search " + q);
        try {
            int offset = (page - 1) * 10;

            if (category == null || category.isBlank()) {
                if (q == null || q.isBlank()) {
                    logger.info("Searching on page " + page + " sorted by" + sortBy + " " + ascending);
                    return new ResponseEntity<>(productRepository.getAll(offset, sortBy, ascending), HttpStatus.OK);
                } else {
                    logger.info("Searching for " + q + " on page " + page + " sorted by" + sortBy + " " + ascending);
                    return new ResponseEntity<>(productRepository.searchProductByWord(q, offset, sortBy, ascending), HttpStatus.OK);
                }
            } else {
                if (q == null || q.isBlank()) {
                    logger.info("Searching for " + category + " on page " + page + " sorted by" + sortBy + " " + ascending);
                    return new ResponseEntity<>(productRepository.searchProductByCategory(category, offset, sortBy, ascending), HttpStatus.OK);
                } else {
                    logger.info("Searching for " + q + " and " + category + " on page " + page + " sorted by" + sortBy + " " + ascending);
                    return new ResponseEntity<>(productRepository.searchProductByWordAndCategory(q, category, offset, sortBy, ascending), HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            logger.error("Could not search for a product");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling POST-requests for retrieving all of a users products
     * @param userId the id of the user to retrieve all the products for
     * @return an HTTP response containing a list of all the users products and an HTTP status code
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<UsersProducts> getUsersProducts(@PathVariable int userId) {
        logger.info("Getting users " + userId + "products");
        try {
            User user = userService.getUserById(userId);
            List<Product> products = productRepository.getFromUserId(userId);
            List<ProductImage> images = imageRepository.getImagesForUsersProducts(userId);
            return new ResponseEntity<>(new UsersProducts(user, products, images), HttpStatus.OK);
        }catch (Exception e ){
            logger.error("Getting users products failed");
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling POST-requests for retrieving a users rental history
     * @param userId the id of the user to retrieve the history for
     * @return an HTTP response containing a list of the users history and an HTTP status code
     */
    @GetMapping("/user/{userId}/history")
    public ResponseEntity<List<Product>> getUserRentalHistory(@PathVariable int userId) {
        logger.info("Getting a users rental history" + userId + "products");
        try {
            List<Product> history = productRepository.getUserRentalHistory(userId);
            if (history.isEmpty()) {
                logger.info("Rental history for user " + userId + " is empty.");
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                logger.info("Rental history for user " + userId + " retrieved successfully.");
                return new ResponseEntity<>(history, HttpStatus.OK);
            }
        }catch (Exception e ){
            logger.error("Rental history retrieval failed");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *  Method for handling DELETE-request for deleting a product based on product id and user id
     * @param userId the id of the owner of the product
     * @param productId the id of the product that is to be deleted
     * @return an HTTP response containing a response string and an HTTP status code
     */
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<String> deleteProductWithuserId(@PathVariable int userId, @RequestParam int productId){
        try{
            logger.info("Attempting to delete product");
            if(productRepository.deleteProductWithUserIdAndProductId(userId,productId) == 1){
                logger.info("Deletion for product with product id " + productId + " was successful");
                return new ResponseEntity<>("Deletion for product with product id " + productId + " was successful", HttpStatus.OK);
            }else {
                logger.info("Wrong product id or user id ");
                return new ResponseEntity<>("Wrong product id or user id", HttpStatus.CONFLICT);
            }

        }catch (Exception e){
            logger.error("Deletion was unsuccessful for product with product id " + productId + " for user with user id" + userId);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

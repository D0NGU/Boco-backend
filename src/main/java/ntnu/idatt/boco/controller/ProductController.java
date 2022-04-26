package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.AvailabilityWindow;
import ntnu.idatt.boco.model.Product;
import ntnu.idatt.boco.model.ProductImage;
import ntnu.idatt.boco.model.Rental;
import ntnu.idatt.boco.repository.ImageRepository;
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

/**
 * This class contains methods responsible for handling HTTP requests regarding products.
 */
@CrossOrigin
@RestController
@RequestMapping("api/products")
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired ProductRepository productRepository;
    @Autowired RentalRepository rentalRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired ProductService service;

    /**
     * Method for handling POST-requests for registering a new product
     * @param product the product to be registered
     * @return an HTTP response containing a result message as a String and a HTTP status code
     */
    @PostMapping("/product/create")
    public ResponseEntity<String> newProduct(@RequestBody Product product) {
        logger.info("Creating new product: " + product.getTitle());
        try {
            productRepository.newProduct(product);
            logger.info("Product created");
            return new ResponseEntity<>("Created successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info("Error creating new product");
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling PUT-requests for editing a product
     * @param productId the id of the product
     * @param product the edited product
     * @return an HTTP response containing a result message as a String and a HTTP status code
     */
    @PutMapping("/product/{productId}/edit")
    public ResponseEntity<String> editProduct(@PathVariable int productId,@RequestBody Product product) {
        logger.info("Editing product: " + productId);
        try {
            productRepository.editProduct(product, productId);
            logger.info("Editing product: " + productId);
            return new ResponseEntity<>("Created successfully!", HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Error editing product");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling POST-request for adding new images
     * @param productId the id of the product to add the image to
     * @param image the image to be added
     * @return an HTTP response containing a result message as a String and a HTTP status code
     */
    @PostMapping("/{productId}/image")
    public ResponseEntity<String> newImage(@PathVariable int productId, @RequestBody ProductImage image) {
        logger.info("Adding picture: " + image.getImgName() + " to product " + productId);
        try {
            imageRepository.newPicture(image);
            logger.info("Image saved");
            return new ResponseEntity<>("Created successfully!", HttpStatus.CREATED);
        }catch (Exception e) {
            logger.info("Error saving new product");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling GET-requests for retrieving a products images
     * @param productId the id of the product
     * @return an HTTP response containing a list of product images and a HTTP status code
     */
    @GetMapping("/images/{productId}")
    @ResponseBody
    public ResponseEntity<List<ProductImage>> getImagesByProudctId(@PathVariable int productId) {
        logger.info("Finding images by product id: " + productId);
        try {
            List<ProductImage> images = imageRepository.getImagesByProductId(productId);
            logger.info(images.size() + " images found");
            return new ResponseEntity<>(images, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Error getting images");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling GET-requests for retrieving a product by id
     * @param productId the id of the product
     * @return an HTTP response containing the retrieved product and a HTTP status code
     */
    @GetMapping("/{productId}")
    @ResponseBody
    public ResponseEntity<Product> getById(@PathVariable int productId) {
        logger.info("Getting product " + productId);
        try {
            return new ResponseEntity<>(productRepository.getProduct(productId), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Error getting product");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling GET-requests retrieving all products
     * @param page the page the user is getting redirected to
     * @return an HTTP response containing a list of all products and a HTTP status code
     */
    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<List<Product>> getAll(@RequestParam int page) {
        logger.info("Getting list of products");
        try {
            int offset = (page-1)*10;
            return new ResponseEntity<>(productRepository.getAll(offset), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Error getting list of products");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling GET-requests for retrieving all products of a certain category
     * @param category the category of the products
     * @param page the page the user is getting redirected to
     * @return an HTTP response containing a list of all products of a certain category and a HTTP status code
     */
    @GetMapping("/category/{category}")
    @ResponseBody
    public ResponseEntity<List<Product>> getByCategory(@PathVariable String category, @RequestParam int page) {
        logger.info("Getting all products in " + category);
        try {
            int offset = (page-1)*10;
            return new ResponseEntity<>(productRepository.getFromCategory(category, offset), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Error getting list of products by category");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling GET-requests for retrieving a products availability window
     * @param productId the id of the product
     * @return an HTTP response containing a list of the availability windows of the product and a HTTP status code
     */
    @GetMapping("/product/{productId}/availability")
    @ResponseBody
    public ResponseEntity<List<AvailabilityWindow>> getAvailability(@PathVariable int productId) {
        logger.info("Request for availability window for product " + productId);
        try {
            Product product = productRepository.getProduct(productId);
            List<Rental> rentals = rentalRepository.getAcceptedRentals(productId, true);
            return new ResponseEntity<>(service.getAvailability(product, rentals), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Could not get availability for product " + productId);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling GET-requests for searching for products
     * @param q the word to search for
     * @param page the page the user is getting redirected to
     * @return a list of all the products matching the search-word
     */
    @GetMapping("/search/")
    @ResponseBody
    public ResponseEntity<List<Product>> getProductFromSearch(@RequestParam String q, @RequestParam int page) {
        logger.info("Request for a search " + q);
        try{
            int offset = (page-1)*2;
            logger.info("Searching for " + q + " on page " + page);
            return new ResponseEntity<>(productRepository.searchProductByWord(q,offset), HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.info("Could not search for a product");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

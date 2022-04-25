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

    @PostMapping("/product/create")
    public ResponseEntity<String> newProduct(@RequestBody Product product) {
        logger.info("Creating new product: " + product.getTitle());
        try {
            productRepository.newProduct(product);
            logger.info("Product created");
            return new ResponseEntity<>("Created successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info("Error creating new product");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<List<Product>> getAll() {
        // TODO Trenger kanskje ikke å hente alle produkt i hele databasen? 10 nyeste? Søkefunksjon i backend?
        logger.info("Getting list of products");
        try {
            return new ResponseEntity<>(productRepository.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Error getting list of products");
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
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/availability")
    @ResponseBody
    public ResponseEntity<List<AvailabilityWindow>> getAvailability(@PathVariable int productId) {
        logger.info("Request for availability window for product " + productId);
        try {
            Product product = productRepository.getProduct(productId);
            List<Rental> rentals = rentalRepository.getRentals(productId);
            return new ResponseEntity<>(service.getAvailability(product, rentals), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Could not get availability for product " + productId);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @ResponseBody
    public List<Product> getProductFromSearch(@RequestParam String q) {
        return productRepository.searchProductByWord(q);
    }
}

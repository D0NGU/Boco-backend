package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.controller.AuthController;
import ntnu.idatt.boco.controller.ProductController;
import ntnu.idatt.boco.controller.RentalController;
import ntnu.idatt.boco.model.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * This test class contains test-methods for the methods in the controllers
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test") // Makes the tests use the test database instead of the normal one
@SpringBootTest
public class ControllerTests {
    @Autowired
    AuthController authController;
    @Autowired
    ProductController productController;
    @Autowired
    RentalController rentalController;

    @Test
    @Order(1)
    public void successfullyAddedUser(){
        User user = new User();
        user.setEmail("d");
        user.setFname("d");
        user.setLname("d");
        user.setPassword("d");

        assertEquals("Registered successfully!", authController.registerNewAccount(user).getBody());
    }

    @Test
    @Order(2)
    public void unSuccessfullyAddedUser(){
        User user = new User();
        user.setEmail("d");
        user.setFname("d");
        user.setLname("d");
        user.setPassword("d");

        assertEquals("Duplicate email", authController.registerNewAccount(user).getBody());
    }

    @Test
    @Order(3)
    public void successfulLogIn(){
        assertEquals("Successfull login", authController.loginUser(new LoginRequest("d","d")).getBody());
    }

    @Test
    @Order(4)
    public void unSuccessfulLogIn(){
        assertEquals("Wrong password", authController.loginUser(new LoginRequest("d","wrongPassword")).getBody());
    }

    @Test
    @Order(5)
    public void successfullyRegisteredNewProduct() {
        Product product = new Product(1, "Hammer", "Description", "Address", 20.0,
                false, new Date(122, 5, 12), new Date(122, 5, 16), 1, "verktøy");
        assertEquals("Created successfully!", productController.newProduct(product).getBody());
    }

    @Test
    @Order(6)
    public void successfullyRetrievedAllProducts() {
        Product product = new Product(1, "Hammer", "Description", "Address", 20.0,
                false, new Date(122, 5, 12), new Date(122, 5, 16), 1, "verktøy");
        List<Product> list = new ArrayList<>();
        list.add(product);
        assertEquals(list.toString(), productController.getAll().getBody().toString());
    }

    @Test
    @Order(7)
    public void successfullyEditedProduct(){
        Product product = new Product(1, "Hammer", "Something else", "New address", 200.0,
                false, new Date(122, 5, 12), new Date(122, 5, 16), 1, "verktøy");
        assertEquals("Created successfully!",productController.editProduct("1", product).getBody());
    }

    @Test
    @Order(8)
    public void successfullyRetrievedProductById() {
        Product product = new Product(1, "Hammer", "Something else", "New address", 200.0,
                false, new Date(122, 5, 12), new Date(122, 5, 16), 1, "verktøy");
        assertEquals(product.toString(), productController.getById("1").getBody().toString());
    }

    @Test
    @Order(9)
    public void successfullyRetrievedProductsByCategory() {
        Product product = new Product(1, "Hammer", "Something else", "New address", 200.0,
                false, new Date(122, 5, 12), new Date(122, 5, 16), 1, "verktøy");
        List<Product> list = new ArrayList<>();
        list.add(product);
        assertEquals(list.toString(), productController.getByCategory("verktøy").getBody().toString());
    }

    @Test
    @Order(10)
    public void successfullyRegisteredNewRental() {
        Rental rental = new Rental(1, new Date(122, 5, 12), new Date(122, 5, 13), 1, 1);
        assertEquals("Registered successfully!", rentalController.registerNewRental(rental).getBody());
    }

    @Test
    @Order(11)
    public void successfullyRetrievedRentalsByProductId() {
        Rental rental = new Rental(1, new Date(122, 5, 12), new Date(122, 5, 13), 1, 1);
        List<Rental> list = new ArrayList<>();
        list.add(rental);
        assertEquals(list.toString(), rentalController.getRentals("1").getBody().toString());
    }

    @Test
    @Order(12)
    public void successfullyRetrievedRentalsByProductIdWhenEmpty() {
        assertNull(rentalController.getRentals("2").getBody());
    }


}

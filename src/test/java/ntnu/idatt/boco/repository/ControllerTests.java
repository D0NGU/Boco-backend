package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.controller.AuthController;
import ntnu.idatt.boco.controller.ProductController;
import ntnu.idatt.boco.controller.RentalController;
import ntnu.idatt.boco.controller.UserController;
import ntnu.idatt.boco.model.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * This test class contains test-methods for the methods in the controllers
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")// Makes the tests use the test database instead of the normal one
@SpringBootTest
public class ControllerTests {
    @Autowired
    AuthController authController;
    @Autowired
    ProductController productController;
    @Autowired
    RentalController rentalController;
    @Autowired
    UserController userController;

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
        Product product = new Product(3, "Hammer", "Description", "Address", 20.0,
                false, new Date(122, 5, 12), new Date(122, 5, 16), 2, "verktøy");
        assertEquals("Created successfully!", productController.newProduct(product).getBody());
    }

    @Test
    @Order(6)
    public void successfullyRetrievedAllProducts() {
        Product product1 = new Product(1, "John Deere 7280R", "Pent brukt traktor!", "Myrangvegen 4, 2040 Kløfta", 450.0, false,  new Date(122, 4, 11), new Date(122, 6, 20),1, "kjøretøy");
        Product product2 = new Product(2,"Valtra 34CX", "Meget pent brukt traktor!!", "Myrangvegen 6, 2040 Kløfta", 200.0, false, new Date(122, 2, 1), new Date(122, 9, 25), 1, "kjøretøy");
        Product product = new Product(3, "Hammer", "Description", "Address", 20.0,
                false, new Date(122, 5, 12), new Date(122, 5, 16), 2, "verktøy");
        List<Product> list = new ArrayList<>();
        list.add(product1);
        list.add(product2);
        list.add(product);
        assertEquals(list.toString(), productController.getAll().getBody().toString());
    }

    @Test
    @Order(7)
    public void successfullyEditedProduct(){
        Product product = new Product(3, "Hammer", "Something else", "New address", 200.0,
                false, new Date(122, 5, 12), new Date(122, 5, 16), 2, "verktøy");
        assertEquals("Created successfully!",productController.editProduct("3", product).getBody());
    }

    @Test
    @Order(8)
    public void successfullyRetrievedProductById() {
        Product product = new Product(3, "Hammer", "Something else", "New address", 200.0,
                false, new Date(122, 5, 12), new Date(122, 5, 16), 2, "verktøy");
        assertEquals(product.toString(), productController.getById("3").getBody().toString());
    }

    @Test
    @Order(9)
    public void successfullyRetrievedProductsByCategory() {
        Product product = new Product(3, "Hammer", "Something else", "New address", 200.0,
                false, new Date(122, 5, 12), new Date(122, 5, 16), 2, "verktøy");
        List<Product> list = new ArrayList<>();
        list.add(product);
        assertEquals(list.toString(), productController.getByCategory("verktøy").getBody().toString());
    }

    @Test
    @Order(10)
    public void successfullyRegisteredNewRental() {
        Rental rental = new Rental(1, new Date(122, 5, 14), new Date(122, 5, 16), 3, 2);
        assertEquals("Registered successfully!", rentalController.registerNewRental(rental).getBody());
    }

    @Test
    @Order(11)
    public void successfullyRetrievedRentalsByProductId() {
        Rental rental = new Rental(1, new Date(122, 5, 14), new Date(122, 5, 16), 3, 2);
        List<Rental> list = new ArrayList<>();
        list.add(rental);
        assertEquals(list.toString(), rentalController.getRentals("3").getBody().toString());
    }

    @Test
    @Order(12)
    public void successfullyRetrievedRentalsByProductIdWhenEmpty() {
        assertNull(rentalController.getRentals("2").getBody());
    }

    /*
    @Test
    @Order(13)
    public void successfullyRetrieveAvailabilityWindow() {
        Rental rental = new Rental(1, new Date(122, 5, 12), new Date(122, 5, 12), 1, 1);
        rentalController.registerNewRental(rental);
        AvailabilityWindow availability = new AvailabilityWindow(new Date(122, 5, 12), new Date(122, 5, 13));
        List<AvailabilityWindow> list = new ArrayList<>();
        list.add(availability);
        assertEquals(list.toString(), productController.getAvailability("1").getBody().toString());
    }*/

    @Test
    @Order(14)
    public void successfullyEditedUserPassword() {
        EditUserRequest request = new EditUserRequest("d", "d", "e");
        assertEquals("Successful", userController.editPassword(request).getBody());
    }


    @Test
    @Order(15)
    public void unSuccessfullyEditedUserPassword() {
        EditUserRequest request = new EditUserRequest("d", "f", "d");
        assertEquals("Wrong old password", userController.editPassword(request).getBody());
    }

    @Test
    @Order(16)
    public void successfullyRetrievedProductsByUser() {
        Product product = new Product(3, "Hammer", "Something else", "New address", 200.0,
                false, new Date(122, 5, 12), new Date(122, 5, 16), 2, "verktøy");
        List<Product> list = new ArrayList<>();
        list.add(product);
        assertEquals(list.toString(), userController.getUsersProducts("2").getBody().toString());
    }

    @Test
    @Order(17)
    public void successfullyDeletedUser() {
        User user = new User();
        user.setEmail("d");
        user.setFname("d");
        user.setLname("d");
        user.setPassword("e");

        assertEquals("Deletion was successful", userController.deleteUser(user).getBody());
    }
}

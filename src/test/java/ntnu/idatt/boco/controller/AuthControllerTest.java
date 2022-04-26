package ntnu.idatt.boco.controller;
/**
import ntnu.idatt.boco.model.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test") // To use 'application-test.properties', 'schema.sql' and 'data-test.sql' to define H2 database
@SpringBootTest
public class AuthControllerTest {
    @Autowired AuthController authController;
    
    User mockUserInput() {
        User user = new User(); 
        user.setFname("Gon");
        user.setLname("Freecss");
        user.setPassword("WhaleIsland123");
        user.setEmail("gon.fre@hunter.www");
        return user;
    }

    @Test
    @Order(1)
    public void registerNewAccount_successfullyAddedUser_true() {
        assertEquals(201, authController.registerNewAccount(mockUserInput()).getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void registerNewAccount_unsuccessfullyAddedUser_false() {
        assertEquals(409, authController.registerNewAccount(mockUserInput()).getStatusCodeValue());
    }

    @Test
    @Order(3)
    public void loginUser_successfulLogIn_true() {
        assertEquals(200, authController.loginUser(new LoginRequest("gon.fre@hunter.www", "WhaleIsland123")).getStatusCodeValue());
    }

    @Test
    @Order(4)
    public void loginUser_unsuccessfulLogIn_false() {
        assertEquals(403, authController.loginUser(new LoginRequest("gon.fre@hunter.www", "M070999G")).getStatusCodeValue());
    }
}
**/
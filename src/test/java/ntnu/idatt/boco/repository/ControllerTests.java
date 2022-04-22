package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.controller.AuthController;
import ntnu.idatt.boco.controller.ProductController;
import ntnu.idatt.boco.model.LoginRequest;
import ntnu.idatt.boco.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
public class ControllerTests {
    @Autowired
    AuthController authController;
    @Autowired
    ProductController productController;


    @Test
    public void successfullyAddedUser(){
        User user = new User();
        user.setEmail("d");
        user.setFname("d");
        user.setLname("d");
        user.setPassword("d");

        assertEquals("Registered successfully!", authController.registerNewAccount(user).getBody());
    }

    @Test
    public void unSuccessfullyAddedUser(){
        User user = new User();
        user.setEmail("d");
        user.setFname("d");
        user.setLname("d");
        user.setPassword("d");

        assertEquals("Duplicate email", authController.registerNewAccount(user).getBody());
    }

    @Test
    public void successfulLogIn(){
        assertEquals("Successfull login", authController.loginUser(new LoginRequest("d","d")).getBody());
    }

    @Test
    public void unSuccessfulLogIn(){
        assertEquals("Wrong password", authController.loginUser(new LoginRequest("d","wrongPassword")).getBody());
    }

}

package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest
public class UserControllerTest {
    @Autowired UserController userController;

    @Test
    @Order(1)
    public void successfullyEditedUserPassword() {
        EditUserRequest request = new EditUserRequest("t.est@tset.edu", "test123", "123test");
        assertEquals(200, userController.editPassword(request).getStatusCodeValue());
    }


    @Test
    @Order(2)
    public void unSuccessfullyEditedUserPassword() {
        EditUserRequest request = new EditUserRequest("t.est@tset.edu", "tset321", "123test");
        assertEquals(403, userController.editPassword(request).getStatusCodeValue());
    }

    @Test
    @Order(3)
    public void unSuccessfullyEditedUserPasswordWithInvalidUserData() {
        EditUserRequest request = new EditUserRequest(null, null, null);
        assertEquals(500, userController.editPassword(request).getStatusCodeValue());
    }

    @Test
    @Order(4)
    public void unSuccessfullyGetUserById() {
        assertEquals(500, userController.getUserByUserId(36).getStatusCodeValue());
    }

    @Test
    @Order(5)
    public void successfullyDeletedUser() {
        User user = new User();
        user.setEmail("t.est@tset.edu");
        user.setFname("test");
        user.setLname("tester");
        user.setPassword("123test");

        assertEquals(200, userController.deleteUser(user).getStatusCodeValue());
    }

    @Test
    @Order(6)
    public void unSuccessfullyDeletedUser() {
        User user = new User();
        user.setEmail(null);
        user.setFname("test");
        user.setLname("tester");
        user.setPassword("123test");

        assertEquals(500, userController.deleteUser(user).getStatusCodeValue());
    }
}

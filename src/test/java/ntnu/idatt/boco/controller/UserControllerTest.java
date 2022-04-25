package ntnu.idatt.boco.controller;
/**
import ntnu.idatt.boco.model.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
    public void successfullyRetrievedProductsByUser() {
        List<Product> list = Arrays.asList(new Product(1, "Dragon hunter crossbow", "A dragonbane weapon requiring 65 Ranged to wield.", "Gilenor", 600.0, false,  LocalDate.of(2022, 4, 11), LocalDate.of(2022, 6, 20),1, "hvitevarer"));
        assertEquals(list.toString(), userController.getUsersProducts(1).getBody().toString());
    }

    @Test
    @Order(4)
    public void successfullyDeletedUser() {
        User user = new User();
        user.setEmail("t.est@tset.edu");
        user.setFname("test");
        user.setLname("tester");
        user.setPassword("123test");

        assertEquals(200, userController.deleteUser(user).getStatusCodeValue());
    }
}
**/
package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.EditUserRequest;
import ntnu.idatt.boco.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.time.LocalDate;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS) // Runs SpringBoot again before testing this class. Resets database.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest
public class UserControllerTest {
    @Autowired UserController userController;
    private User testUser = new User(3,"navn", "naver", "navn@naver.no","", "password", LocalDate.of(2022, 4,11));
    @Test
    @Order(1)
    public void successfullyAddUser(){
        assertEquals(201,userController.saveUser(testUser).getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void unSuccessfulAddUser(){
        assertEquals(409,userController.saveUser(testUser).getStatusCodeValue());
    }


    @Test
    @Order(3)
    public void successfulEditUse(){
        EditUserRequest editetUser = new EditUserRequest(3,"navn@naver.com","password","password1");
        assertEquals(200,userController.editUser(editetUser).getStatusCodeValue());
    }

    @Test
    @Order(4)
    public void unSuccessfulEditUser(){
        EditUserRequest editedUser = new EditUserRequest(3,"navn@naver.no","pass","password1");
        assertEquals(403,userController.editUser(editedUser).getStatusCodeValue());
    }

    @Test
    @Order(5)
    public void successfullyGetUserByEmail(){
        assertEquals(200, userController.getUserByEmail("navn@naver.com").getStatusCodeValue());
    }

    @Test
    @Order(6)
    public void unSuccessfulGetUserByEmail(){
        assertEquals(404, userController.getUserByEmail("navn@ndejrirbaver.com").getStatusCodeValue());
    }

    @Test
    @Order(7)
    public void successfullyGetUserById(){
        assertEquals(200, userController.getUserById(3).getStatusCodeValue());
    }

    @Test
    @Order(8)
    public void unSuccessfulGetUserById(){
        assertEquals(404, userController.getUserById(10).getStatusCodeValue());
    }

    @Test
    @Order(9)
    public void unSuccessfullyDeleteUser(){
        assertEquals(401,userController.deleteUser(3,"pass").getStatusCodeValue());
    }

    @Test
    @Order(10)
    public void successfullyDeleteUser(){
        assertEquals(200,userController.deleteUser(3,"password1").getStatusCodeValue());
    }
}

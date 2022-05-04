package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.Alert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS) // Runs SpringBoot again before testing this class. Resets database.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest
public class AlertControllerTest {
    @Autowired
    AlertController alertController;

    private final Alert TEST_ALERT1 = new Alert(1, "alert", LocalDate.of(2022, 1, 11), false, 1, 1);
    private final Alert TEST_ALERT2 = new Alert(1, null, null, false, 1, 15);

    @Test
    @Order(1)
    public void successfullyRegisterNewAlert() {
        assertEquals(200, alertController.newAlert(TEST_ALERT1).getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void unsuccessfullyRegisterNewAlert() {
        assertEquals(500, alertController.newAlert(TEST_ALERT2).getStatusCodeValue());
    }

    @Test
    @Order(3)
    public void successfullyGetUserAlert() {
        List<Alert> list =Arrays.asList(TEST_ALERT1);
        assertEquals(list.toString(), alertController.getUserAlerts(1).getBody().toString());
    }

    @Test
    @Order(4)
    public void successfullyGetUserAlertWhenEmpty() {
        assertEquals(200, alertController.getUserAlerts(16).getStatusCodeValue());
    }

    @Test
    @Order(5)
    public void successfullyGetUnseenAlert() {
        List<Alert> list =Arrays.asList(TEST_ALERT1);
        assertEquals(list.toString(), alertController.getUserAlerts(1).getBody().toString());
    }

    @Test
    @Order(6)
    public void successfullyMarkAlertAsSeen() {
        assertEquals(200, alertController.changeAlertToSeen(1).getStatusCodeValue());
    }

    @Test
    @Order(7)
    public void successfullyMarkAlertAsSeenWhenNoChangeHappens() {
        assertEquals(200, alertController.changeAlertToSeen(1).getStatusCodeValue());
    }

    @Test
    @Order(8)
    public void successfullyGetUnseenAlertWhenEmpty() {
        assertNull(alertController.getUnSeenAlerts(1).getBody());
    }

    @Test
    @Order(9)
    public void successfullyDeleteAlert() {
        assertEquals(200, alertController.deleteAlert(1).getStatusCodeValue());
    }

    @Test
    @Order(10)
    public void successfullyDeleteAlertWhenNotFound() {
        assertEquals(400, alertController.deleteAlert(1).getStatusCodeValue());
    }

}

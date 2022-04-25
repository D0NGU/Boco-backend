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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest
public class RentalControllerTest {
    @Autowired RentalController rentalController;
    private final Rental TEST_RENTAL = new Rental(1, LocalDate.of(2022, 5, 14), LocalDate.of(2022, 5, 16), true, 1, 2);
    
    @Test
    @Order(1)
    public void successfullyRegisteredNewRental() {
        assertEquals(201, rentalController.registerNewRental(TEST_RENTAL).getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void successfullyRetrievedRentalsByProductId() {
        List<Rental> list = Arrays.asList(TEST_RENTAL);
        assertEquals(list.toString(), rentalController.getRentals(1).getBody().toString());
    }

    @Test
    @Order(3)
    public void successfullyRetrievedRentalsByProductIdWhenEmpty() {
        assertNull(rentalController.getRentals(6).getBody());
    }

    /* @Test
    @Order(4)
    public void successfullyRetrieveAvailabilityWindow() {
        Rental rental = new Rental(1, LocalDate.of(2022, 5, 12), LocalDate.of(2022, 5, 12), 1, 1);
        rentalController.registerNewRental(rental);
        AvailabilityWindow availability = new AvailabilityWindow(LocalDate.of(2022, 5, 12), LocalDate.of(2022, 5, 13));
        List<AvailabilityWindow> list = new ArrayList<>();
        list.add(availability);
        assertEquals(list.toString(), productController.getAvailability(1).getBody().toString());
    } */
}

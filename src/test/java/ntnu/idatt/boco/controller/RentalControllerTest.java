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
    private final Rental TEST_RENTAL = new Rental(2, LocalDate.of(2022, 7, 14), LocalDate.of(2022, 7, 16), false, 1, 2);
    private final Rental TEST_RENTAL2 = new Rental(1, LocalDate.of(2022, 11, 12), LocalDate.of(2022, 12, 23), true, 1, 2);
    private final Rental TEST_RENTAL3 = new Rental(1, LocalDate.of(2022, 3, 12), LocalDate.of(2022, 12, 23), true, 1, 2);
    
    @Test
    @Order(1)
    public void successfullyRegisteredNewRental() {
        assertEquals(201, rentalController.registerNewRental(TEST_RENTAL).getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void unSuccessfullyRegisteredNewRental() {
        assertEquals(409, rentalController.registerNewRental(TEST_RENTAL3).getStatusCodeValue());
    }

    @Test
    @Order(3)
    public void successfullyRetrievedRentalsByProductId() {
        List<Rental> list = Arrays.asList(TEST_RENTAL, TEST_RENTAL2);
        assertEquals(list.toString(), rentalController.getRentals(1).getBody().toString());
    }

    @Test
    @Order(4)
    public void successfullyRetrievedRentalsByProductIdWhenEmpty() {
        assertNull(rentalController.getRentals(6).getBody());
    }

    @Test
    @Order(5)
    public void successfullyRetrievedAcceptedRentalsByProductId() {
        List<Rental> list = Arrays.asList(TEST_RENTAL2);
        assertEquals(list.toString(), rentalController.getAcceptedRentals(1, true).getBody().toString());
    }

    @Test
    @Order(6)
    public void successfullyAcceptedRental() {
        assertEquals(200, rentalController.acceptRental(TEST_RENTAL.getRentalId()).getStatusCodeValue());
    }

    @Test
    @Order(7)
    public void unSuccessfullyAcceptedRental() {
        assertEquals(400, rentalController.acceptRental(TEST_RENTAL.getRentalId()).getStatusCodeValue());
    }

    @Test
    @Order(8)
    public void successfullyRetrievedAcceptedRentalsByProductIdWhenEmpty() {
        assertNull(rentalController.getAcceptedRentals(6, true).getBody());
    }

    @Test
    @Order(9)
    public void successfullyRetrievedNonAcceptedRentalsByProductIdWhenEmpty() {
        assertNull(rentalController.getAcceptedRentals(1, false).getBody());
    }

    @Test
    @Order(10)
    public void successfullyDeleteRental() {
        assertEquals(200, rentalController.deleteRental(2).getStatusCodeValue());
    }

    @Test
    @Order(11)
    public void unSuccessfullyDeleteRental() {
        assertEquals(400, rentalController.deleteRental(2).getStatusCodeValue());
    }

}

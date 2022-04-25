package ntnu.idatt.boco.service;

import ntnu.idatt.boco.model.AvailabilityWindow;
import ntnu.idatt.boco.model.Product;
import ntnu.idatt.boco.model.Rental;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    ProductService service;

    @Test
    public void getAvailableTest() {
        Product product = new Product();

        product.setAvailableFrom(LocalDate.parse("2022-01-03"));
        product.setAvailableTo(LocalDate.parse("2022-02-01"));

        List<Rental> rentals = new ArrayList<>();

        rentals.add(new Rental());
        rentals.add(new Rental());

        rentals.get(0).setDateFrom(LocalDate.parse("2022-01-13"));
        rentals.get(0).setDateTo(LocalDate.parse("2022-01-21"));

        rentals.get(1).setDateFrom(LocalDate.parse("2022-01-04"));
        rentals.get(1).setDateTo(LocalDate.parse("2022-01-10"));

        List<AvailabilityWindow> expected = new ArrayList<>();

        expected.add(new AvailabilityWindow(LocalDate.parse("2022-01-03"), LocalDate.parse("2022-01-03")));
        expected.add(new AvailabilityWindow(LocalDate.parse("2022-01-11"), LocalDate.parse("2022-01-12")));
        expected.add(new AvailabilityWindow(LocalDate.parse("2022-01-22"), LocalDate.parse("2022-02-01")));

        assertEquals(expected, service.getAvailability(product, rentals));
    }
}

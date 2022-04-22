package ntnu.idatt.boco.service;

import ntnu.idatt.boco.model.AvailabilityWindow;
import ntnu.idatt.boco.model.Product;
import ntnu.idatt.boco.model.Rental;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.sql.Date;
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

        product.setAvailableFrom(Date.valueOf("2022-01-01"));
        product.setAvailableTo(Date.valueOf("2022-02-01"));

        List<Rental> rentals = new ArrayList<>();

        rentals.add(new Rental());
        rentals.add(new Rental());

        rentals.get(0).setDateFrom(Date.valueOf("2022-01-16"));
        rentals.get(0).setDateTo(Date.valueOf("2022-01-17"));

        rentals.get(1).setDateFrom(Date.valueOf("2022-01-05"));
        rentals.get(1).setDateTo(Date.valueOf("2022-01-10"));

        List<AvailabilityWindow> expected = new ArrayList<>();

        expected.add(new AvailabilityWindow(Date.valueOf("2022-01-01"), Date.valueOf("2022-01-04")));
        expected.add(new AvailabilityWindow(Date.valueOf("2022-01-11"), Date.valueOf("2022-01-15")));
        expected.add(new AvailabilityWindow(Date.valueOf("2022-01-18"), Date.valueOf("2022-02-01")));

        assertEquals(expected, service.getAvailability(product, rentals));
    }
}

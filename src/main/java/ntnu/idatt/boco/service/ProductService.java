package ntnu.idatt.boco.service;

import ntnu.idatt.boco.model.AvailabilityWindow;
import ntnu.idatt.boco.model.Product;
import ntnu.idatt.boco.model.Rental;
import ntnu.idatt.boco.repository.ProductRepository;
import ntnu.idatt.boco.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    RentalRepository rentalRepository;

    /**
     * Method to get a list of time windows where a product is available for rental.
     * The method takes a product and uses its listing period as lower and upper bound.
     * It then takes a list of rentals sorted on from date to create a list of availability windows.
     * @param product The product to find availability for
     * @param rentals List of all rentals for the product
     * @return list of windows where the product is available.
     */
    public List<AvailabilityWindow> getAvailability(Product product, List<Rental> rentals) {
        List<AvailabilityWindow> requested = new ArrayList<>();
        List<AvailabilityWindow> available = new ArrayList<>();

        for (Rental rental : rentals) {
            requested.add(new AvailabilityWindow(rental.getDateFrom(), rental.getDateTo()));
        }

        available.add(new AvailabilityWindow(product.getAvailableFrom(), requested.get(0).getFrom()));

        for (int i = 0; i < (requested.size() - 1); i++) {
            available.add(new AvailabilityWindow(requested.get(i).getTo(), requested.get(i+1).getFrom()));
        }

        available.add(new AvailabilityWindow(requested.get(requested.size()-1).getTo(), product.getAvailableTo()));

        return available;
    }
}

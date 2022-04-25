package ntnu.idatt.boco.service;

import ntnu.idatt.boco.model.AvailabilityWindow;
import ntnu.idatt.boco.model.Product;
import ntnu.idatt.boco.model.Rental;
import ntnu.idatt.boco.repository.ProductRepository;
import ntnu.idatt.boco.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
     * It then takes a list of rentals to create a list of availability windows.
     * @param product The product to find availability for
     * @param rentals List of all rentals for the product
     * @return list of windows where the product is available.
     */
    public List<AvailabilityWindow> getAvailability(Product product, List<Rental> rentals) {
        List<AvailabilityWindow> requested = new ArrayList<>();
        List<AvailabilityWindow> available = new ArrayList<>();

        System.out.println(product.getAvailableFrom());
        System.out.println(product.getAvailableTo());
        if(!rentals.isEmpty()){

            for (Rental rental : rentals) {
            /*
            */
                requested.add(new AvailabilityWindow(rental.getDateFrom(), rental.getDateTo()));
            }

            requested.sort(Comparator.comparing(AvailabilityWindow::getFrom));

            if (!product.getAvailableFrom().equals(requested.get(0).getFrom())) {
                available.add(new AvailabilityWindow(product.getAvailableFrom(), requested.get(0).getFrom().plusDays(-1)));
            }

            for (int i = 0; i < (requested.size() - 1); i++) {
                available.add(new AvailabilityWindow(requested.get(i).getTo().plusDays(1), requested.get(i+1).getFrom().plusDays(-1)));
            }

            if (!requested.get(requested.size()-1).getTo().equals(product.getAvailableTo())) {
                available.add(new AvailabilityWindow(requested.get(requested.size()-1).getTo().plusDays(+1), product.getAvailableTo()));
            }

        }else{
            available.add(new AvailabilityWindow(product.getAvailableFrom(), product.getAvailableTo()));
        }

        return available;
    }
}

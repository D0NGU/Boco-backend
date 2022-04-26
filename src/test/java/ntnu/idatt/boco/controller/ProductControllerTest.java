package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.*;
import ntnu.idatt.boco.repository.ImageRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS) // Runs SpringBoot again before testing this class. Resets database.
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest
public class ProductControllerTest {
    @Autowired ProductController productController;
    @Autowired ImageRepository imageRepository;

    // Test products
    private final Product EXISTING_TEST_PRODUCT = new Product(1, "Dragon hunter crossbow", "A dragonbane weapon requiring 65 Ranged to wield.", "Gilenor", 600.0, false,  LocalDate.of(2022, 4, 11), LocalDate.of(2022, 6, 20),1, "hvitevarer");
    private final Product TEST_PRODUCT = new Product(2, "Abyssal whip", "A one-handed melee weapon which requires an Attack level of 70 to wield.", "Gilenor", 300.0, false, LocalDate.of(2022, 2, 1), LocalDate.of(2022, 9, 25), 1, "utstyr");
    // Test images
    private final ProductImage IMAGE_1 = new ProductImage(1, "Dragon Hunter Crossbow (b)", "iVBORw0KGgoAAAANSUhEUgAAAB4AAAAdCAMAAACKeiw+AAAArlBMVEUAAAC+ubSxrKWrpZ+moJqkn5ijnZahm5WfmpSblpCalY+WkYuVj4mPioWNiIOJhX+Ef3p/e3Z7d3J5dXB3c251cWxwbWlua2ZsaWRqZmJnZGBqYmJlYl1nYGBlXl1jXFthWlpdV1dbVVVYUlJWUFBUTU1OTEhRSkpOSUhMRkZIQ0NFQEBCPT0/Ojo8Nzc5NDQ1MDBwGA0yLi4sKiooJiYkIiIgHR0aGBgVEREAAAFP5aFAAAAAAXRSTlMAQObYZgAAAURJREFUeNp9k9ligkAMRdPW7vtmV0WLVFadwoTh/P+P9QFBkNa8Mecmk7kJIv8EyJ4givdwvqIk3fA/ZPhxkq0QEdABZxLEabY2CIcHWu32wXQZp6vCIQK2er+lq+DDC5O1dQ4RwVbA81UrYDIPIqNlfYCtEAHuEEQEzw+z3DbqGosIwjXCcp2afHtXi2vFNCq010jhtl8sQmN77yAvt9men+X9V2LU1U0J8yBeDUwoFB4Q4WsRZXC+m14ojwj+Mk4ZzgBT2DtoC+9Wdya/Meus8el0wCeX6ert5WLMX5uC5y9PT45H9981vthpbj6LE62awTLqGxPMFlleOdfY16vOh+eH5seVZbnhnHVsnk39MAFKVS0B4KiDp0FW1VtnrZaqtshNZ78Itb2zsKrWWvtatVMm6pTaxPizczbc86e9/1CT8QtWG0W80/xpdwAAAABJRU5ErkJggg==".getBytes(), 1);
    private final ProductImage IMAGE_2 = new ProductImage(2, "Dragon Hunter Crossbow", "iVBORw0KGgoAAAANSUhEUgAAAB4AAAAdCAMAAACKeiw+AAABhlBMVEUAAADa3t7S19jR1tfO1NTL0dHK0NDFzM3Fy83Dy8vDysvCycrByMi/x8e+xsa8xMW5wcK3wMG0vr6yu7yvubqtuLmstreqtbaos7SosrSlsbGkr6+irq+irK+fq6ufqaudqKmbp6iWoqOWoaOVoKGUn5+SnZ2Qm5uQmpuPmZqNl5iNlpiLlZaJlJWIkpOGkJCRohGFj4+Fjo+DjY2OnxGDjI2Biot/iIl/h4mImBF9hod7hYWFlRF6g4R2f39/jhF5kRF0fX18ixFye3t0ixFweHl5hw1udnd2hA1sdHVsc3VrcnNygA1pcHBmbm5vfA1mbW5seA1iamppdA1gZ2dqYmJdZGVkcA1hbQ1aYGFhWlpdaQlVW1tbVVVaZAlSWFhYUlJVYAlNVFRNUlRUTU1RWwlITU5NVglOSUhGSkxMRkZDSEhJUQlIQ0NFTAlFQEA9QkJCPT0/SAk/Ojo6QgA0OTk3QgA5NDQ1PAA1MDAyLi4wNQAsKioiJgAgHR0aHQARFRUAAAGkjtt3AAAAAXRSTlMAQObYZgAAAXlJREFUeAF9wQtbiwEAgNEXkcgtUUgRGRGlyaULikSfZoZhSUgLTbaVsnJ5/7mntWVfnjqHzShb8FyHbMr6yOXrUiT/sfHClVt3BTQlG7irqb2rd2hUfP08uaSEWHP6Unf/qwVBY8sreZV17th3tuP+228LAsaW1R9ZZY07D52IPHz/SQGMLwnqoghY29DWOzauFBmbl1ViXjz1oGfsqVJikJMSsbrtxbSyzsG0lHm45fG4VLAvJSXuPXL1mVQyGs+JANY1dwwpIQ6+1Kxg3fFIv85JJTuHE2bF+uZIjyBhRgeDjDa1DyiAhHgv2jczOtQ7rQBmJMQ726e6B1YKUwUBlBCrDp6cnJx8s/hLAOekkrsPnL/4YX5eWeVnqWDN/sYbT3Kz6VkpUv5xW+3R1kff0+8mJj7KKjNS5p7qhtYuNZVMJFMWzUiZVceu/RHQIIgnE7GR2zc7fysltnyRIh0O4rFYEIwUvv6UNZ6RMkvyi1ImG5mVrUjRX2a7iXfMrNs2AAAAAElFTkSuQmCC", 1);

    @Test
    @Order(1)
    public void successfullyRetrievedProductsByUser() {
        List<Product> list = Arrays.asList(new Product(1, "Dragon hunter crossbow", "A dragonbane weapon requiring 65 Ranged to wield.", "Gilenor", 600.0, false,  LocalDate.of(2022, 4, 11), LocalDate.of(2022, 6, 20),1, "hvitevarer"));

        assertEquals(list.toString(), productController.getUsersProducts(1).getBody().getProducts().toString());
    }

    @Test
    @Order(2)
    public void successfullyRegisteredNewProduct() {
        assertEquals(201, productController.newProduct(TEST_PRODUCT).getStatusCodeValue());
    }

    @Test
    @Order(3)
    public void successfullyRetrievedAllProducts() {        
        List<Product> list = Arrays.asList(TEST_PRODUCT, EXISTING_TEST_PRODUCT);

        assertEquals(list.toString(), productController.getProductFromSearch(null, null, 1,"price", true).getBody().toString());
    }

    @Test
    @Order(4)
    public void successfullyEditedProduct() {
        Product editedTestProduct = new Product("The dragon hunter crossbow possesses a passive effect that increases ranged accuracy by 30% and damage by 25% when fighting draconic creatures.", "Gilenor", 350.0, false, LocalDate.of(2022, 2, 1), LocalDate.of(2022, 9, 25), "utstyr");
        assertEquals(200, productController.editProduct(2, editedTestProduct).getStatusCodeValue());
    }

    @Test
    @Order(5)
    public void successfullyRetrievedProductById() {
        assertEquals(EXISTING_TEST_PRODUCT.toString(), productController.getById(1).getBody().toString());
    }

    @Test
    @Order(6)
    public void successfullyRetrievedProductsByCategory() {
        List<Product> list = Arrays.asList(EXISTING_TEST_PRODUCT);
        assertEquals(list.toString(), productController.getProductFromSearch(null,"hvitevarer", 1, "price",true).getBody().toString());
    }

    @Test
    @Order(7)
    public void successfullyStoreAndRetrieveImageByProduct() {
        List<ProductImage> expected = new ArrayList<>();
        imageRepository.newPicture(IMAGE_1);
        imageRepository.newPicture(IMAGE_2);

        expected.add(IMAGE_1);
        expected.add(IMAGE_2);

        List<ProductImage> acutal = productController.getImagesByProudctId(1).getBody();

        assertEquals(expected.size(), acutal.size());
        for(int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), acutal.get(i));
        }
    }
    @Test
    @Order(8)
    public void successfullyRetrieveAvailabilityWindow() {
        AvailabilityWindow availability = new AvailabilityWindow(LocalDate.of(2022, 7, 12), LocalDate.of(2022, 11, 11));
        AvailabilityWindow availability2 = new AvailabilityWindow(LocalDate.of(2022, 12,24), LocalDate.of(2022,12,24));
        List<AvailabilityWindow> list = Arrays.asList(availability,availability2);
        assertEquals(list.toString(), productController.getAvailability(1).getBody().toString());
    }

    @Test
    @Order(9)
    public void successfulSearchWithWord(){
        List<Product> list = Arrays.asList(EXISTING_TEST_PRODUCT);
        assertEquals(list.toString(),productController.getProductFromSearch("dragonbane", null, 1,"price", true).getBody().toString());
    }

    @Test
    @Order(10)
    public void successfulSearchWithWordAndCategory(){
        List<Product> list = Arrays.asList(EXISTING_TEST_PRODUCT);
        assertEquals(list.toString(),productController.getProductFromSearch("dragonbane", "hvitevarer", 1,"price", true).getBody().toString());
    }
}

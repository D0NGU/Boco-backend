package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.ContactForm;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS) // Runs SpringBoot again before testing this class. Resets database.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest
public class ContactFormControllerTest {
    @Autowired
    ContactFormController contactFormController;

    public final ContactForm TEST_CONTACT_FORM = new ContactForm(1, "test", "tester", "t.est@tset.edu", "Veldig bra nettside", 1);
    public final ContactForm INVALID_TEST_CONTACT_FORM = new ContactForm(1, "test", "tester", null, null, 14);

    @Test
    @Order(1)
    public void successfullyRegisterNewContactForm() {
        assertEquals(201, contactFormController.registerNewContactForm(TEST_CONTACT_FORM).getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void unsuccessfullyRegisterNewContactForm() {
        assertEquals(500, contactFormController.registerNewContactForm(INVALID_TEST_CONTACT_FORM).getStatusCodeValue());
    }
}

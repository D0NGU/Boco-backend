package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.ContactForm;
import ntnu.idatt.boco.repository.ContactFormRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class contains methods responsible for handling HTTP requests regarding contact forms.
 */
@CrossOrigin
@RestController
@RequestMapping("api/contact")
public class ContactFormController {
    Logger logger = LoggerFactory.getLogger(ContactFormController.class);
    @Autowired
    ContactFormRepository contactFormRepository;

    /**
     * Endpoint for adding a new contact form
     * @param contactForm {@link ContactForm} object
     * @return message and HttpStatus
     */
    @PostMapping
    public ResponseEntity<String> registerNewContactForm(@RequestBody ContactForm contactForm) {
        logger.info("New contact form received");
        try {
            contactFormRepository.newContactForm(contactForm);
            logger.info("Contact form was created successfully.");
            return new ResponseEntity<>("Contact form created", HttpStatus.CREATED);
        }
        catch(Exception e) {
            logger.error("Error saving contact form");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

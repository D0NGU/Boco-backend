package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.model.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * This class is responsible for communication with the database regarding {@link ContactForm}.
 */
@Repository
public class ContactFormRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Method to add a new contact form to the database
     * @param contactForm contact form to add
     * @return the amount of rows affected (1 if added one contact form)
     */
    public int newContactForm(ContactForm contactForm) {
        System.out.println(contactForm.getEmail());
        return jdbcTemplate.update("INSERT INTO contact_forms(fname, lname, email, comment, user_id) VALUES (?,?,?,?,?)",
                new Object[]{contactForm.getFname(), contactForm.getLname(), contactForm.getEmail(), contactForm.getComment(), contactForm.getUserId()});
    }
}

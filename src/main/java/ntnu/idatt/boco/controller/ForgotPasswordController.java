package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import net.bytebuddy.utility.RandomString;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * This class contains methods responsible for handling HTTP requests regarding reset password requests.
 */
@RestController
@RequestMapping("/api")
public class ForgotPasswordController {
    @Autowired private JavaMailSender mailSender;
    @Autowired private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

    /**
     * Endpoint for sending a "forgot password" email to a user
     * @param email the email address to send the "forgot password" mail to
     * @return {@code true} if the users email address is valid, {@code false} if the email address is invalid
     */
    @PostMapping("/forgot_password")
    public ResponseEntity<Boolean> processForgotPassword(@RequestParam String email) {
        String token = RandomString.make(30);
        try{
            if(userRepository.getUser(email) != null){
                logger.info("Reset password - sending mail to " + email);
                userRepository.updatePasswordToken(email,token);
                String resetPasswordLink = "http://localhost:8081/password/reset?token=" + token; // IKKE SIKKERT DENNE FUNKER PÅ MOBIL
                sendEmail(email,resetPasswordLink);
                return new ResponseEntity<>(true, HttpStatus.OK);
            }else {
                logger.info("Reset password - email not found");
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.error("Error sending reset password mail to " + email);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for sending an email to a recipient
     * @param recipientEmail email address of the recipient
     * @param link url to send
     */
    public void sendEmail(String recipientEmail, String link){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("kontakt.boco@gmail.com","BoCo Brukerstøtte");
            helper.setTo(recipientEmail);
            String subject = "Tilbakestilling av passord";
            String content = "<p>Hei,</p>"
                    +"<p>Trykk på lenken under for å tilbakestille passordet</p>"
                    + "<p><a href=\"" + link + "\"> Tilbakestill passord</a></p>"
                    + "<br>"
                    + "<p>Ignorer denne meldingen om du ikke etterspurte "
                    + "tilbakestilling av passordet ditt.</p>"
                    + "<br>"
                    + "<br>"
                    + "<p>Mvh Borrow Community</p>";

            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    /**
     * Endpoint for resetting a users password
     * @param token a token for identifying the user
     * @param password the new password
     * @return {@code true} if the user was found, {@code false} if the user could not be found
     */
    @PutMapping("/reset_password")
    public ResponseEntity<Boolean> processRestPassword(@RequestParam String token, @RequestParam String password){
        try {
            if(userRepository.getByResetPasswordToken(token) != null){
                userRepository.resetJustPassword(token, password);
                logger.info("Reset password - successful");
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                logger.info("Reset password - could not find user");
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            logger.error("Error resetting password");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

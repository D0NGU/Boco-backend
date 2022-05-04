package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import net.bytebuddy.utility.RandomString;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api")
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/forgot_password")
    public ResponseEntity<Boolean> processForgotPassword(@RequestParam String email) {
        String token = RandomString.make(30);
        try{
            if(userRepository.getUser(email) != null){
                userRepository.updatePasswordToken(email,token);
                String resetPasswordLink = "http://localhost:8081/reset_password?token=" + token;
                sendEmail(email,resetPasswordLink);
                return new ResponseEntity<>(true, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void sendEmail(String recipientEmail, String link){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("kontakt.boco@gmail.com","BoCo brukerstøtte");
            helper.setTo(recipientEmail);

            String subject = "tilbakestil passord";
            String content = "<p>Hei,</p>"
                    +"<p>Trykk på lenken under til å tilbakestille passordet</p>"
                    + "<p><a href=\"" + link + "\"> TilbakeStill passord</a></p>"
                    + "<br>"
                    + "<p>Ignorer denne meldingen om du ikke etterspurte "
                    + "om å tilbakestille passordet.</p>";

            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        }catch (MessagingException | UnsupportedEncodingException e){
            e.printStackTrace();
        }


    }
}

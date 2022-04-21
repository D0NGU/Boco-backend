package ntnu.idatt.boco.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ntnu.idatt.boco.model.User;
import ntnu.idatt.boco.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired UserRepository databaseRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> registerNewAccount(@RequestBody User user) {
        logger.info("Signup Requested for " + user.getEmail());
        try {
            databaseRepository.saveUserToDatabase(user);
            logger.info("Success - user registered");
            return new ResponseEntity<>("Registered successfully!", HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            logger.info("Error registering user - Email in use");
            e.printStackTrace();
            return new ResponseEntity<>("Duplicate email", HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.info("Error registering user");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signin")
    public void signinAccount(@RequestBody User user) {
        logger.info("Login requested by " + user.getEmail());
        try {
            databaseRepository.saveUserToDatabase(user);
        } catch (Exception e) {
            logger.info("Login error");
            e.printStackTrace();
            //return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signout")
    public void signoutAccount(@RequestBody User user) {
        logger.info("Logout requested by " + user.getEmail());
        try {
            // TODO Signout user
        } catch (Exception e) {
            logger.info("Logout error");
            e.printStackTrace();
            //return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

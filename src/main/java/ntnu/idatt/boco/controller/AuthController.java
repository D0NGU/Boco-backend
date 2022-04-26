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

import ntnu.idatt.boco.model.LoginRequest;
import ntnu.idatt.boco.model.User;
import ntnu.idatt.boco.repository.UserRepository;
import ntnu.idatt.boco.security.Encryption;

/**
 * This class contains methods responsible for handling HTTP requests regarding user registration and login/logout.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired UserRepository databaseRepository;

    /**
     * Method for handling POST-requests for registering a new user
     * @param user the user to be registered
     * @return an HTTP response containing a result message as a String and a HTTP status code
     */
    @PostMapping("/signup")
    public ResponseEntity<String> registerNewAccount(@RequestBody User user) {
        logger.info(user.getEmail() + ": Signup Requested");
        try {
            databaseRepository.saveUserToDatabase(user);
            logger.info(user.getEmail() + ": User registered");
            return new ResponseEntity<>("Registered successfully!", HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            logger.info(user.getEmail() + ": Error - Email in use");
            return new ResponseEntity<>("Duplicate email", HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.info(user.getEmail() + ": Error registering user");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling POST-requests for user login
     * @param login a LoginRequest containing an email and password
     * @return an HTTP response containing a result message as a String and a HTTP status code
     */
    @PostMapping("/signin")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest login) {
        logger.info(login.getEmail() + ": Login requested");
        try {
            // Check if user exists
            if (!databaseRepository.existsByEmail(login.getEmail())) {
                logger.info(login.getEmail() + ": User does not exist");
                return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
            }
            // Check if password is correct
            byte[] expectedHash = databaseRepository.getHashedPasswordByEmail(login.getEmail());
            byte[] salt = databaseRepository.getSaltByEmail(login.getEmail());
            if (Encryption.isExpectedPassword(login.getPassword(), salt, expectedHash)) {
                logger.info(login.getEmail() + ": Successfull login");
                return new ResponseEntity<>("Successfull login", HttpStatus.OK);
            } else {
                logger.info(login.getEmail() + ": Wrong password");
                return new ResponseEntity<>("Wrong password", HttpStatus.FORBIDDEN);
            }  
        } catch (Exception e) {
            logger.info("Login error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling POST-requests for user signout
     * @param user the user requesting the signout
     */
    @PostMapping("/signout")
    public void signoutAccount(@RequestBody User user) {
        // TODO
    }
}

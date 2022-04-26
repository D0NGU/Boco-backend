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
     * @return status code and the userID:
     *          {@code 201} if user registered, 
     *          {@code 409} if duplicate email,
     *          {@code 500} if error
     */
    @PostMapping("/signup")
    public ResponseEntity<Integer> registerNewAccount(@RequestBody User user) {
        String email = user.getEmail();
        logger.info(email + ": Signup Requested");
        
        try {
            databaseRepository.saveUserToDatabase(user);
            logger.info(email + ": User registered");
            // Return userId to client with status 201 (Created)
            int id = databaseRepository.getIdByEmail(email);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } 
        
        catch (DuplicateKeyException dke) {
            logger.info(email + ": Error - Email in use");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } 
        
        catch (Exception e) {
            logger.error(email + ": Error registering user");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling POST-requests for user login
     * @param login a LoginRequest containing an email and password
     * @return status code and the userID:
     *          {@code 200} if success,
     *          {@code 403} if wrong password, 
     *          {@code 404} if duplicate email,
     *          {@code 500} if error
     */
    // POST-request because the parameters in GET get stored all over the place for caching reasons.
    @PostMapping("/signin")
    public ResponseEntity<Integer> loginUser(@RequestBody LoginRequest login) {
        String email = login.getEmail();
        logger.info(email + ": Login requested");
        try {
            // Check if user exists
            if (!databaseRepository.existsByEmail(email)) {
                logger.info(email + ": User does not exist");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            int id = databaseRepository.getIdByEmail(email);
            // Check if password is correct
            byte[] expectedHash = databaseRepository.getHashedPasswordByEmail(email);
            byte[] salt = databaseRepository.getSaltByEmail(email);
            if (Encryption.isExpectedPassword(login.getPassword(), salt, expectedHash)) {
                // Return OK if correct password
                logger.info(email + ": Successfull login");
                return new ResponseEntity<>(id, HttpStatus.OK);
            } else {
                // Return 403 if wrong password
                logger.info(email + ": Wrong password");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }  
        } catch (Exception e) {
            logger.error(email + ": Login error");
            e.printStackTrace();
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

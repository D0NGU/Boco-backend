package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.EditUserRequest;
import ntnu.idatt.boco.model.User;
import ntnu.idatt.boco.repository.ProductRepository;
import ntnu.idatt.boco.repository.UserRepository;
import ntnu.idatt.boco.security.Encryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class contains methods responsible for handling HTTP requests regarding users.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired UserRepository userRepository;
    @Autowired ProductRepository productRepository;

    /**
     * Method for handling DELETE-requests for deleting a user
     * @param user the user to be deleted
     * @return an HTTP response containing a result message as a String and a HTTP status code
     */
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody User user) {
        logger.info("Delete requested by " + user.getEmail());
        try {
            byte[] salt = userRepository.getSaltByEmail(user.getEmail());
            byte[] hashedPass = userRepository.getHashedPasswordByEmail(user.getEmail());
            boolean correctPass = Encryption.isExpectedPassword(user.getPassword(), salt, hashedPass);
            if (correctPass) {
                userRepository.deleteUser(user);
                logger.info(user.getEmail() + ": Successfully was deleted");
                return new ResponseEntity<>("Deletion was successful", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Wrong password", HttpStatus.FORBIDDEN);
            }

        } catch (Exception e) {
            logger.error("Delete failed");
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for handling POST-requests for editing a user
     * @param user the user to be edited
     * @return an HTTP response containing a result message as a String and a HTTP status code
     */
    @PutMapping
    public ResponseEntity<String> editPassword(@RequestBody EditUserRequest user){
        logger.info("Edit user requested by " + user.getEmail());
        try{
            byte[] salt = userRepository.getSaltByEmail(user.getEmail());
            byte[] hashedPass = userRepository.getHashedPasswordByEmail(user.getEmail());
            boolean correctPass = Encryption.isExpectedPassword(user.getOldPassword(), salt, hashedPass);
            if(correctPass){
                userRepository.changePasswordInDatabase(user.getEmail(), user.getNewPassword());
                logger.info(user.getEmail() + ": Successfully edited password");
                return new ResponseEntity<>("Successful", HttpStatus.OK);
            }else{
                logger.info(user.getEmail() + ": Wrong old password");
                return new ResponseEntity<>("Wrong old password", HttpStatus.FORBIDDEN);
            }
        }catch (Exception e ){
            logger.error("Edit user failed");
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


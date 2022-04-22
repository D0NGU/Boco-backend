package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.Product;
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

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody User user){
        logger.info("Delete requested by " + user.getEmail());
        try{
            byte[] salt = userRepository.getSaltByEmail(user.getEmail());
            byte[] hashedPass = userRepository.getHashedPasswordByEmail(user.getEmail());
            boolean correctPass = Encryption.isExpectedPassword(user.getPassword(), salt, hashedPass);
            if(correctPass){
                userRepository.deleteUser(user);
                logger.info(user.getEmail() + ": Successfully was deleted");
                return new ResponseEntity<>("Deletion was successful", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Wrong password", HttpStatus.FORBIDDEN);
            }

        }catch (Exception e){
            logger.info("Delete failed");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editPassword(@RequestBody User user, @RequestParam String newPassword){
        logger.info("Edit user requested by " + user.getEmail());
        try{
            byte[] salt = userRepository.getSaltByEmail(user.getEmail());
            byte[] hashedPass = userRepository.getHashedPasswordByEmail(user.getEmail());
            boolean correctPass = Encryption.isExpectedPassword(user.getPassword(), salt, hashedPass);
            if(correctPass){
                userRepository.changePasswordInDatabase(user, newPassword);
                logger.info(user.getEmail() + ": Successfully edited password");
                return new ResponseEntity<>("Successful", HttpStatus.OK);
            }else{
                logger.info(user.getEmail() + ": Wrong old password");
                return new ResponseEntity<>("Wrong old password", HttpStatus.FORBIDDEN);
            }
        }catch (Exception e ){
            logger.info("Edit user failed");
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/products/{userId}")
    @ResponseBody
    public List<Product> getUsersProducts(@PathVariable String userId) {
        return productRepository.getFromUserId(userId);
    }
}


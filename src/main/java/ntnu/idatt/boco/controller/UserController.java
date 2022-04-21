package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.User;
import ntnu.idatt.boco.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserRepository userRepository;

    @DeleteMapping("/delete")
    public ResponseEntity<Integer> deleteUser(@PathVariable User user){
        logger.info("Delete requested by " + user.getEmail());
        try{
            return new ResponseEntity<>(userRepository.deleteUser(user), HttpStatus.OK);
        }catch (Exception e){
            logger.info("Delete failed");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<Integer> editPassword(@RequestBody User user, @RequestParam String newPassword){
        logger.info("Edit user requested by " + user.getEmail());
        try{
            return new ResponseEntity<>(userRepository.changePasswordInDatabase(user, newPassword), HttpStatus.OK);
        }catch (Exception e ){
            logger.info("Edit user failed");
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


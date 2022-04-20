package ntnu.idatt.boco.controller;

import ntnu.idatt.boco.model.User;
import ntnu.idatt.boco.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserRepository userRepository;

    @DeleteMapping("/delete/{email}")
    public void deleteUser(@PathVariable int email){
        logger.info("Delete requested by " + email);
        try{
            //TODO delete user, need method in repo
        }catch (Exception e){
            logger.info("Delete failed");
            e.printStackTrace();
        }
    }

    @PostMapping("/edit")
    public void editPassword(@RequestBody User user){
        logger.info("Edit user requested by " + user.getEmail());
        try{
            //TODO method to get password in repo
        }catch (Exception e ){
            logger.info("Edit user failed");
            e.printStackTrace();
        }
    }
}


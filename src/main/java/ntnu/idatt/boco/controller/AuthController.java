package ntnu.idatt.boco.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ntnu.idatt.boco.model.User;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signup")
    public void registerNewAccount(@RequestBody User user) {
        logger.info("Signup Requested for " + user.getEmail());
        // TODO Signup new account
    }

    @PostMapping("/signin")
    public void signinAccount(@RequestBody User user) {
        logger.info("Login requested by " + user.getEmail());
        // TODO Signin new account
    }

    @PostMapping("/signout")
    public void signoutAccount(@RequestBody User user) {
        logger.info("Logout requested by " + user.getEmail());
        // TODO Signin new account
    }
}

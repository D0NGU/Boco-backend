package ntnu.idatt.boco.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ntnu.idatt.boco.model.EditUserRequest;
import ntnu.idatt.boco.model.User;
import ntnu.idatt.boco.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController 
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userService;
    Logger logger = LoggerFactory.getLogger(RentalController.class);

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>>getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<User>saveUser(@RequestBody User user) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        System.out.println(user.toString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }


    @DeleteMapping("/user/delete")
    public ResponseEntity<String> deleteUser(@RequestBody User user) {
        logger.info("Deleting user: {}", user.getEmail());
        userService.deleteUser(user);
        return new ResponseEntity<>("Deletion was successful", HttpStatus.OK);
    }


    @PostMapping("/user/edit")
    public ResponseEntity<?> editUser(@RequestBody EditUserRequest editUserRequest){
        logger.info("Edit user : {}",editUserRequest.getEmail());
        if (BCrypt.checkpw(editUserRequest.getOldPassword(), userRepository.getUser(editUserRequest.getEmail()).getPassword())) {
            return new ResponseEntity<>(userService.editUser(editUserRequest), HttpStatus.OK);
        }
        else {
            logger.info("User " + editUserRequest.getEmail() + " used wrong password");
            return new ResponseEntity<>("Wrong password", FORBIDDEN);
        }
    }

    @GetMapping("/user/get/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        logger.info("Getting user by email: {}", email);
        return new ResponseEntity<>(userService.getUser(email), HttpStatus.OK);
    }

    @GetMapping("/user/get/")
    public ResponseEntity<User> getUserById(@RequestParam int userId){
        logger.info("Getting user by id: {}", userId);
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + (10 * 60 * 1000)))
                        .withIssuer(request.getRequestURI().toString())
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}


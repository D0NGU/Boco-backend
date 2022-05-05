package ntnu.idatt.boco.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ntnu.idatt.boco.model.EditUserRequest;
import ntnu.idatt.boco.model.User;
import ntnu.idatt.boco.repository.ReviewRepository;
import ntnu.idatt.boco.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Class containing endpoints to do with users
 * @see User
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userService;
    private final ReviewRepository reviewRepository;
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired UserRepository userRepository;

    /**
     * Endpoint for getting all users
     * @return a list of users and HttpStatus
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    /**
     * Endpoint for registering a new user
     * @param user the user-info
     * @return the user
     */
    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        logger.info("Registrering new user");
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }


    /**
     * Endpoint for deleting a user
     * @param userId the id of the user to delete
     * @return a result message
     */
    @DeleteMapping("/user/delete")
    public ResponseEntity<String> deleteUser(@RequestParam int userId, @RequestParam String password) {
        if ((BCrypt.checkpw(password, userRepository.getUserById(userId).getPassword()))) {
            userService.deleteUser(userService.getUserById(userId));
            return new ResponseEntity<>("User " + userId + " - deleted", HttpStatus.OK);
        } else {
            logger.info("User " + userId + " - used wrong password or username");
            return new ResponseEntity<>("Wrong password", HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Endpoint for editing a user
     * @param editUserRequest an object containing all the necessary information for editing a user
     * @return an HTTP response containing HTTP status code
     */
    @PostMapping("/user/edit")
    public ResponseEntity<?> editUser(@RequestBody EditUserRequest editUserRequest){
        if (BCrypt.checkpw(editUserRequest.getOldPassword(), userRepository.getUserById(editUserRequest.getId()).getPassword())) {
            logger.info("User : " + editUserRequest.getEmail() + " - edited");
            return new ResponseEntity<>(userService.editUser(editUserRequest), HttpStatus.OK);
        }
        else {
            logger.info("User " + editUserRequest.getEmail() + " - used wrong password or username");
            return new ResponseEntity<>("Wrong password", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/user/get/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        logger.info("Getting user " + email);
        return new ResponseEntity<>(userService.getUser(email), HttpStatus.OK);
    }

    /**
     * Endpoint for checking user vertification
     * <p>
     * Users should only be vertified if they have received atleast 10 reviews
     * with an average score over 4. Their account also have to be over 1 month old.
     * @param userId the user
     * @return {@code true} if vertified, {@code false} if not
     */
    @GetMapping("/user/{userId}/vertified")
    public ResponseEntity<Boolean> isUserVertified(@PathVariable int userId){
        logger.info("Checking vertification of user " + userId);
        try {
            // Get info needed
            double avg_stars = 0;
            int amount = 0;
            LocalDate signup = LocalDate.now();
            try {
                avg_stars = reviewRepository.getAverageUserReviews(userId);
                amount = reviewRepository.getAmountOfSubjectReviews(userId);
                signup = getUserById(userId).getBody().getSignup();
            } catch (Exception e) {
                logger.info("User " + userId + " - not vertified");
                return new ResponseEntity<>(false, HttpStatus.OK);
            }

            // Check vertification
            // From product owner: "kan være å ha fått minst 10 anmeldelser som har et gjennomsnitt over 4 stjerner, og vært medlem i minst en måned"
            if (avg_stars>4.00 && amount>=10 && signup.isBefore(LocalDate.now().minusMonths(1))) {
                logger.info("User " + userId + " - vertified");
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
            logger.info("User " + userId + " - not vertified");
            return new ResponseEntity<>(false, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("User " + userId + " - could not get vertified status");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/get/")
    public ResponseEntity<User> getUserById(@RequestParam int userId){
        logger.info("User " + userId + " - getting user");
        try {
            return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
        }catch (EmptyResultDataAccessException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user/{userid}/description")
    public ResponseEntity<?> newDescription(@PathVariable int userid, @RequestBody String description) {
        logger.info("User " + userid + " - adding description");
        userRepository.newDescription(userid, description);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("user/{userid}/description")
    public ResponseEntity<String> getDescription(@PathVariable int userid) {
        logger.info("User " + userid + " - getting description");
        return new ResponseEntity<>(userService.getDescription(userid), HttpStatus.OK);
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

    /**
     * Method for uploading a profile picture
     * @param base64 base 64 encoded image
     * @param userId
     */
    @PutMapping("/{userId}/picture")
    public ResponseEntity<String> upload(@RequestBody ImgString base64, @PathVariable int userId) {
        logger.info("User " + userId + " - setting new profile picture");
        try {
            byte[] picBlob = Base64.getDecoder().decode(base64.getImg());
            userRepository.setPicture(picBlob, userId);
            return new ResponseEntity<>("Picture uploaded?", HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method for getting a profile picture
     * @param userId
     * @return base 64 encode image
     */
    @GetMapping("/{userId}/picture")
    public ResponseEntity<String> getPicture(@PathVariable int userId) {
        logger.info("User " + userId + " - getting profile picture");
        try {
            Byte[] blob = userRepository.getPicture(userId);
            byte[] b = new byte[blob.length];
            for (var x = 0; x <blob.length; x++) {
                b[x] = (byte) blob[x];
            }
            String pic64 = Base64.getEncoder().encodeToString(b);
            return new ResponseEntity<>(pic64, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


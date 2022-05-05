package ntnu.idatt.boco.repository;

import lombok.RequiredArgsConstructor;
import ntnu.idatt.boco.model.EditUserRequest;
import ntnu.idatt.boco.model.Review;
import ntnu.idatt.boco.model.User;
import ntnu.idatt.boco.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for communication with the database regarding {@link User}.
 */
@Repository
@RequiredArgsConstructor
public class UserRepository implements UserService, UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    @Autowired JdbcTemplate jdbcTemplate;

    /**
     * Method for saving a new user to the database
     * @param user the user to save
     * @return the saved user
     */
    @Override
    public User saveUser(User user) {
        logger.info("Saving user: " + user.getEmail());
        jdbcTemplate.update("INSERT INTO user(fname, lname, password, email) VALUES (?,?,?,?)",
                user.getFname(), user.getLname(), passwordEncoder.encode(user.getPassword()), user.getEmail());
        return user;
    }

    /**
     * Method for retrieving a user by email
     * @param email the email of the user to retrieve
     * @return the retrieved user
     */
    @Override
    public User getUser(String email) {
        logger.info("Fetching user: " + email);
        User user = jdbcTemplate.queryForObject("SELECT * FROM user WHERE email = ?",
                BeanPropertyRowMapper.newInstance(User.class), email);
        logger.info("Found user " + user.getFname() +" "+ user.getLname());
        return user;
    }

    /**
     * Method for retrieving a user by id
     * @param id the id of the user to retrieve
     * @return the retrieved user
     */
    @Override
    public User getUserById(Integer id) {
        logger.info("Fetching user: " + id);
        return jdbcTemplate.queryForObject("SELECT * FROM user WHERE id = ?",
                BeanPropertyRowMapper.newInstance(User.class), id);
    }

    /**
     * Method for deleting a user
     * @param user the user to delete
     */
    @Override
    public void deleteUser(User user) {
        logger.info("deleting user: " + user.getId());
        jdbcTemplate.update("DELETE FROM user WHERE id = ?", user.getId());
    }

    /**
     * Method for editing a user
     * @param editUserRequest an object containing all the necessary information for editing a user
     * @return the edited user
     */
    @Override
    public User editUser(EditUserRequest editUserRequest) {
        logger.info("Editing user: " + editUserRequest.getEmail());
        jdbcTemplate.update("UPDATE user SET email = ?, password = ? WHERE id = ?", new Object[]{editUserRequest.getEmail(), passwordEncoder.encode(editUserRequest.getNewPassword()), editUserRequest.getId()});
        return getUserById(editUserRequest.getId());
    }

    /**
     * Method for retrieving all users
     * @return a list of all the users
     */
    @Override
    public List<User> getUsers() {
        logger.info("fetching all users");
        return jdbcTemplate.query("SELECT * FROM user", BeanPropertyRowMapper.newInstance(User.class));
    }

    /**
     *
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        User user = getUser(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    /**
     * Method for registering a new user description
     * @param id the id of the user
     * @param description the new description to register
     */
    public void newDescription(int id, String description) {
        jdbcTemplate.update("UPDATE  user SET description=? WHERE id=?", new Object[]{description, id});
    }

    /**
     * Method for retrieving a user description
     * @param id the id of the user
     * @return a string containing the user description
     */
    public String getDescription(int id) {
        return jdbcTemplate.queryForObject("SELECT description FROM user WHERE id= ?", String.class, id);
    }

    /**
     * Method for updating a users reset_password_token
     * @param email the email of the user
     * @param token the new reset_password_token
     */
    public void updatePasswordToken(String email, String token){
        jdbcTemplate.update("update user SET reset_password_token=? WHERE email=?;", new Object[]{token,email});
    }

    /**
     * Method for retrieving a user by their reset_password_token
     * @param token the reset_password_token of the user to find
     * @return the retrieved user
     */
    public User getByResetPasswordToken(String token){
        return jdbcTemplate.queryForObject("SELECT * FROM user WHERE reset_password_token=?", BeanPropertyRowMapper.newInstance(User.class), token);
    }

    /**
     * Method for resetting a users password
     * @param token the users reset_password_token
     * @param newPassword the new password
     */
    public void resetJustPassword(String token, String newPassword){
        jdbcTemplate.update("UPDATE user SET password=? WHERE reset_password_token=?", new Object[]{passwordEncoder.encode(newPassword),token});
    }

    /**
     * Method for retrieving a users profile picture
     * @param id the id of the user to retrieve the picture from
     * @return a byte array containing the profile picture
     */
    public Byte[] getPicture(int id) {
        return jdbcTemplate.queryForObject("SELECT profilePic FROM user WHERE id= ?", Byte[].class, id);
    }

    /**
     * Method for setting a users profile picture
     * @param id the id of the user to set the picture for
     * @param picBlob a byte array containing the profile picture
     */
    public void setPicture(byte[] picBlob, int id) {
        jdbcTemplate.update("UPDATE user SET profilePic = ? WHERE id = ?", new Object[]{picBlob, id});
    }
}

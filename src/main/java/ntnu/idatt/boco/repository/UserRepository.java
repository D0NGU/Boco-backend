package ntnu.idatt.boco.repository;

import lombok.RequiredArgsConstructor;
import ntnu.idatt.boco.model.EditUserRequest;
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

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserService, UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    @Autowired JdbcTemplate jdbcTemplate;

    @Override
    public User saveUser(User user) {
        logger.info("Saving user: " + user.getEmail());
        jdbcTemplate.update("INSERT INTO user(fname, lname, password, email) VALUES (?,?,?,?)",
                user.getFname(), user.getLname(), passwordEncoder.encode(user.getPassword()), user.getEmail());
        return user;
    }

    @Override
    public User getUser(String email) {
        logger.info("Fetching user: " + email);
        User user = jdbcTemplate.queryForObject("SELECT * FROM user WHERE email = ?",
                BeanPropertyRowMapper.newInstance(User.class), email);
        logger.info("Found user " + user.getFname() +" "+ user.getLname());
        return user;
    }

    @Override
    public User getUserById(Integer id) {
        logger.info("Fetching user: " + id);
        return jdbcTemplate.queryForObject("SELECT * FROM user WHERE id = ?",
                BeanPropertyRowMapper.newInstance(User.class), id);
    }

    @Override
    public void deleteUser(User user) {
        logger.info("deleting user: " + user.getId());
        jdbcTemplate.update("DELETE FROM user WHERE id = ?", user.getId());
    }

    @Override
    public User editUser(EditUserRequest editUserRequest) {
        logger.info("Editing user: " + editUserRequest.getEmail());
        jdbcTemplate.update("UPDATE user SET email = ?, password = ? WHERE email = ?", new Object[]{editUserRequest.getEmail(), passwordEncoder.encode(editUserRequest.getNewPassword()), editUserRequest.getEmail()});
        return getUser(editUserRequest.getEmail());
    }

    @Override
    public List<User> getUsers() {
        logger.info("fetching all users");
        return jdbcTemplate.query("SELECT * FROM user", BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        User user = getUser(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public void newDescription(int id, String description) {
        jdbcTemplate.update("UPDATE  user SET description=? WHERE id=?", new Object[]{description, id});
    }

    public String getDescription(int id) {
        return jdbcTemplate.queryForObject("SELECT description FROM user WHERE id= ?", String.class, id);
    }
}

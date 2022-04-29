package ntnu.idatt.boco.repository;

import lombok.RequiredArgsConstructor;
import ntnu.idatt.boco.model.EditUserRequest;
import ntnu.idatt.boco.model.Role;
import ntnu.idatt.boco.model.User;
import ntnu.idatt.boco.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserService {
    private final PasswordEncoder passwordEncoder;

    @Autowired JdbcTemplate jdbcTemplate;
    @Override
    public User saveUser(User user) {
        jdbcTemplate.update("INSERT INTO user(fname, lname, password, email) VALUES (?,?,?,?,?)",
                user.getFname(), user.getLname(), user.getPassword(), user.getEmail());
        return user;
    }

    @Override
    public Role saveRole(Role role) {
        return null;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
    }

    @Override
    public User getUser(String email) {
        return jdbcTemplate.queryForObject("SELECT * FROM user WHERE email = ?",
                BeanPropertyRowMapper.newInstance(User.class), email);
    }

    @Override
    public User getUserById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM user WHERE id = ?",
                BeanPropertyRowMapper.newInstance(User.class), id);
    }

    @Override
    public void deleteUser(User user) {
        jdbcTemplate.update("DELETE FROM user WHERE id = ?", user.getId());
    }

    @Override
    public User editUser(EditUserRequest editUserRequest) {
        jdbcTemplate.update("UPDATE user SET email = ?, password = ? WHERE id = ?", new Object[]{editUserRequest.getEmail(), passwordEncoder.encode(editUserRequest.getNewPassword()), editUserRequest.getId()});
        return getUserById(editUserRequest.getId());
    }

    @Override
    public List<User> getUsers() {
        return null;
    }
}

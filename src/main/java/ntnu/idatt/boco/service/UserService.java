package ntnu.idatt.boco.service;

import ntnu.idatt.boco.model.Role;
import ntnu.idatt.boco.model.User;

import java.util.List;
import java.util.Optional;

/**
 * interface to represent UserService functionality
 */

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    User getUserById(Integer id);
    void deleteUser(User user);
    List<User> getUsers();
}
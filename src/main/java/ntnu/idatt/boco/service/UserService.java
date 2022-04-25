package ntnu.idatt.boco.service;

import ntnu.idatt.boco.model.Role;
import ntnu.idatt.boco.model.User;

import java.util.List;

/**
 * interface to represent UserService functionality
 */

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
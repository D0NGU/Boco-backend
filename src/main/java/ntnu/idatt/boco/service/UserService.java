package ntnu.idatt.boco.service;

import ntnu.idatt.boco.model.EditUserRequest;
//import ntnu.idatt.boco.model.Role;
import ntnu.idatt.boco.model.User;

import java.util.List;

/**
 * interface to represent UserService functionality
 */

public interface UserService {
    User saveUser(User user);
    User getUser(String username);
    User getUserById(Integer id);
    void deleteUser(User user);
    User editUser(EditUserRequest editUserRequest);
    List<User> getUsers();
}
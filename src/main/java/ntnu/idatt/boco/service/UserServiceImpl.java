package ntnu.idatt.boco.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ntnu.idatt.boco.model.Role;
import ntnu.idatt.boco.model.User;
import ntnu.idatt.boco.usrRepo.RoleRepo;
import ntnu.idatt.boco.usrRepo.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if(user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("saving user: {}", user.getEmail());
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setFname(user.getFname());
            user.setEmail(user.getEmail());
            user.setLname(user.getLname());
            return userRepo.save(user);
        }catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving role: {}", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("adding role: {} to user: {}", roleName, email);
        User user = userRepo.findByEmail(email);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String email) {
        log.info("fetching user: {}", email);
        return userRepo.findByEmail(email);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepo.getById(id);
    }

    @Override
    public void deleteUser(User user) {
        userRepo.deleteById(user.getId());
    }


    @Override
    public List<User> getUsers() {
        log.info("fetching all users");
        return userRepo.findAll();
    }


}

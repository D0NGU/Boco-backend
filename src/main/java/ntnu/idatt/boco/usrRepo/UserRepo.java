package ntnu.idatt.boco.usrRepo;

import ntnu.idatt.boco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
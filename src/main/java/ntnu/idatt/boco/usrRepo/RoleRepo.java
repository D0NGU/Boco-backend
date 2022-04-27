package ntnu.idatt.boco.usrRepo;

import ntnu.idatt.boco.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
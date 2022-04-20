package ntnu.idatt.boco.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ntnu.idatt.boco.model.User;
import ntnu.idatt.boco.security.Encryption;

@Repository
public class UserRepository {
    @Autowired private JdbcTemplate jdbcTemplate;

    public int saveUserToDatabase(User user) {
        byte[] salt = Encryption.getNextSalt();
        String saltString = new String(salt);
        String hashedPassword = new String(Encryption.hash(user.getPassword().toCharArray(), salt));
        return jdbcTemplate.update("INSERT INTO users (fname, lname, password, email, salt) VALUES (?,?,?,?,?);",
                    new Object[] { user.getFname(), user.getLname(), hashedPassword, user.getEmail(), saltString });
    }
}

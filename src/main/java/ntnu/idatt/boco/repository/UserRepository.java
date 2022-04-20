package ntnu.idatt.boco.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ntnu.idatt.boco.model.User;
import ntnu.idatt.boco.security.Encryption;

import java.nio.charset.StandardCharsets;

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

    public String getHashedPasswordFromDatabase(String Email){
        return jdbcTemplate.queryForObject("SELECT password FROM users WHERE email =? ", new Object[]{Email}, String.class);
    }

    public int deleteUser(User user){
        boolean correctPass = Encryption.isExpectedPassword(user.getPassword().toCharArray(), user.getSalt().getBytes(), getHashedPasswordFromDatabase(user.getEmail()).getBytes());
        if(correctPass){
            return jdbcTemplate.update("DELETE FROM users WHERE email = ?;",new Object[]{user.getUserId()});
        }else{
            return 0;
        }
    }
}

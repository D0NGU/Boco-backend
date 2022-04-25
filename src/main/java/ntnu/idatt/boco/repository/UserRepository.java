package ntnu.idatt.boco.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ntnu.idatt.boco.model.User;
import ntnu.idatt.boco.security.Encryption;

/**
 * This class is responsible for communication with the database regarding users.
 */
@Repository
public class UserRepository {
    @Autowired private JdbcTemplate jdbcTemplate;

    /**
     * Method for saving a new user to the database
     * @param user the user to be saved
     * @return the number of rows in the database that was affected
     */
    public int saveUserToDatabase(User user) {
        byte[] salt = Encryption.getNextSalt();
        byte[] hashedPassword = Encryption.hash(user.getPassword(), salt);
        return jdbcTemplate.update("INSERT INTO users (fname, lname, password, email, salt) VALUES (?,?,?,?,?);",
                            new Object[] { user.getFname(), user.getLname(), hashedPassword, user.getEmail(), salt });
    }

    /**
     * Method for retrieving a users hashed password by email
     * @param email the email address of the user to retrieve the hashed password from
     * @return a byte array containing the hashed password
     */
    public byte[] getHashedPasswordByEmail(String email) {
        return jdbcTemplate.queryForObject("SELECT password FROM users WHERE email ='"+email+"';", byte[].class);
    }

    /**
     * Method for retrieving a users salt by email
     * @param email the email address of the user to retrieve the salt from
     * @return a byte array containing the salt
     */
    public byte[] getSaltByEmail(String email) {
        return jdbcTemplate.queryForObject("SELECT salt FROM users WHERE email ='"+email+"';", byte[].class);
    }

    /**
     * Method for checking if a user exists
     * @param email the email address of the user
     * @return true if user exists, false otherwise
     */
    public boolean existsByEmail(String email) {
        String query = "SELECT EXISTS(SELECT * FROM users WHERE email='"+email+"');";
        return jdbcTemplate.queryForObject(query, Boolean.class);
    }

    /**
     * Method for changing a users password
     * @param email the email address of the user to update the password for
     * @param newPassword the users new password
     * @return the number of rows in the database that was affected
     */
    public int changePasswordInDatabase(String email, String newPassword){
        byte[] hashedPassword = Encryption.hash(newPassword, getSaltByEmail(email));
        return jdbcTemplate.update("UPDATE users SET password = ? WHERE email = ?;", hashedPassword, email);
    }

    /**
     * Method for deleting a user from the database
     * @param user the user to be deleted
     * @return the number of rows in the database that was affected
     */
    public int deleteUser(User user){
        return jdbcTemplate.update("DELETE FROM users WHERE email = ?;", user.getEmail());
    }
}

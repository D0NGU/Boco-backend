package ntnu.idatt.boco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
    private int userId;
    private String fname;
    private String lname;
    private String email;
    private String salt;
    private String password;

    public User() {}

    /**
     * Constructor for a user object.
     * @param userId the unique id of the user
     * @param fname the first name of the user
     * @param lname the last name of the user
     * @param email the users email address
     * @param salt a salt used for hashing the users password
     * @param password the password for the user
     */
    public User(int userId, String fname, String lname, String email, String salt, String password) {
        this.userId = userId;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.salt = salt;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }
    public String getFname() {
        return fname;
    }
    public String getLname() {
        return lname;
    }
    public String getEmail() {
        return email;
    }
    @JsonIgnore
    public String getSalt() {
        return salt;
    }
    public String getPassword() {
        return password;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

package ntnu.idatt.boco.model;

/**
 * This class represents a login request.
 */
public class LoginRequest {
    private String email;
    private String password;

    public LoginRequest() {}

    /**
     * Constructor for a login request object.
     * @param email the email address of the user requesting the login
     * @param password the password of the user requesting the login
     */
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

package ntnu.idatt.boco.model;

/**
 * This class represents an edit user request.
 */
public class EditUserRequest {
    private String email;
    private String oldPassword;
    private String newPassword;

    public EditUserRequest() {}

    /**
     * Constructor for an edit user request object.
     * @param email the email address of the user requesting the edit
     * @param oldPassword the old password of the user requesting the edit
     * @param newPassword the new password
     */
    public EditUserRequest(String email, String oldPassword, String newPassword) {
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }
    public String getOldPassword() {
        return oldPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}

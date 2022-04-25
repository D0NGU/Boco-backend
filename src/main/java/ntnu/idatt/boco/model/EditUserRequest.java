package ntnu.idatt.boco.model;

public class EditUserRequest {
    private String email;
    private String oldPassword;
    private String newPassword;

    public EditUserRequest() {}

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

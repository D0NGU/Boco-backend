package ntnu.idatt.boco.model;

public class User {
    private int userId;
    private String fname;
    private String lname;
    private String email;
    private byte[] salt;
    private byte[] password;

    public User() {}

    public User(int userId, String fname, String lname, String email, byte[] salt, byte[] password) {
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

    public byte[] getSalt() {
        return salt;
    }

    public byte[] getPassword() {
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
}

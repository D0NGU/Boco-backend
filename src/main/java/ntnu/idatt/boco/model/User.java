package ntnu.idatt.boco.model;

public class User {
    private String fname;
    private String lname;
    private String password;
    private String email;
    private int id;

    public User() {}
    public User(String fname, String lname, String email, String password) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
    }

    public User(String fname, String lname, String email, String password, int id) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.id = id;
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
    public String getPassword() {
        return password;
    }
    public int getId() {
        return id;
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
    public void setPassword(String password) {
        this.password = password;
    }
    public void setId(int id) {
        this.id = id;
    }
}

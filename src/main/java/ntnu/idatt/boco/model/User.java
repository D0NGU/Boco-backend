package ntnu.idatt.boco.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    private Integer id;
    private String fname;
    private String lname;
    private String email;
    private String desciption;
    private String password;
    private LocalDate signup;

    public User() {
    }

    public User(Integer id, String fname, String lname, String email, String desciption, String password, LocalDate signup) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.desciption = desciption;
        this.password = password;
        this.signup = signup;
    }

    public Integer getId() {
        return id;
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
    public LocalDate getSignup() {
        return signup;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public void setSignup(LocalDate signup) {
        this.signup = signup;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }
}



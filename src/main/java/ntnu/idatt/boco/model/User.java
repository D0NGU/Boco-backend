package ntnu.idatt.boco.model;

import java.time.LocalDate;
import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    private Integer id;
    private String fname;
    private String lname;
    private String email;
    private String description;
    private String password;
    private LocalDate signup;
    private byte[] profilePic;
    private String profile64;

    public User() {
    }

    public User(Integer id, String fname, String lname, String email, String description, String password, LocalDate signup) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.description = description;
        this.password = password;
        this.signup = signup;
    }
    public User(Integer id, String fname, String lname, String email, String description, String password, LocalDate signup, byte[] profilePic) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.description = description;
        this.password = password;
        this.signup = signup;
        this.profilePic = profilePic;
        this.profile64 = Base64.getEncoder().encodeToString(profilePic);
    }

    public User(Integer id, String fname, String lname, String email, String description, String password, LocalDate signup, String profile64) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.description = description;
        this.password = password;
        this.signup = signup;
        this.profile64 = profile64;
        this.profilePic = Base64.getDecoder().decode(profile64);
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
    public String getDescription() {
        return description;
    }
    public byte[] getProfilePic() {
        return profilePic;
    }
    public String getProfile64() {
        return profile64;
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
    public void setDescription(String description) {
        this.description = description;
    }
    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
        this.profile64 = Base64.getEncoder().encodeToString(profilePic);
    }
    public void setProfile64(String profile64) {
        this.profile64 = profile64;
        this.profilePic = Base64.getDecoder().decode(profile64);
    }
}



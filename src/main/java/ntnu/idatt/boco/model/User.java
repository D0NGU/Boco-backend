package ntnu.idatt.boco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fname;
    private String lname;

    @Column(
            unique = true
    )
    private String username;
    private String password;
    @ElementCollection
    private Collection<Role> roles = new ArrayList<>();


    public Long getUserId() {
        return id;
    }
    public String getFname() {
        return fname;
    }
    public String getLname() {
        return lname;
    }
    public String getEmail() {
        return username;
    }
    @JsonIgnore

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
        this.username = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}


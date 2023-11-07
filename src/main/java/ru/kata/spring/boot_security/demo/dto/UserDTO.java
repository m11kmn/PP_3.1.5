package ru.kata.spring.boot_security.demo.dto;

import com.fasterxml.jackson.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
public class UserDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String age;

    private String username;

    private String password;

    @JsonIgnoreProperties("users")
    private List<String> rolesId;

    public List<String> getRolesId() {
        return rolesId;
    }

    public void setRolesId(List<String> rolesId) {
        this.rolesId = rolesId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

package org.spine.iquestionapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Model {
    enum Role {
        SPINE_ADMIN, SPINE_USER, CAREGIVER
    }
    
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String email;
    private String password;
    private String organization;
    private Role role;

    public User() {
    }

    public String serialize() {
        // TODO Auto-generated method stub
        return null;
    }
}

package org.spine.iquestionapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Model {
    enum Role {
        SPINE_ADMIN, SPINE_USER, CAREGIVER
    }
    
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String email;
    private String password;
    private String organization;
    @OneToMany(cascade = CascadeType.ALL)
    private List <Entry> entries = new ArrayList<>();
    private Role role;

    public String serialize() {
        // TODO Auto-generated method stub
        return null;
    }
}

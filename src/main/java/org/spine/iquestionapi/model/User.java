package org.spine.iquestionapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public enum Role {
        SPINE_ADMIN, SPINE_USER, CAREGIVER
    }
    
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @Column(unique=true)
    private String email;
    private String password;
    private String organization;
    @OneToMany(cascade = CascadeType.ALL)
    private List <Entry> entries = new ArrayList<>();
    private Role role;

}

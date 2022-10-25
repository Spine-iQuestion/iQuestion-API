package org.spine.iquestionapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

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
    @JsonIgnore
    private String password;
    private String organization;
    @OneToMany(cascade = CascadeType.ALL)
    private List <Entry> entries = new ArrayList<>();
    private Role role;

}

package org.spine.iquestionapi.model;

import javax.persistence.*;

import org.spine.iquestionapi.service.EntityIdResolver;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", resolver = EntityIdResolver.class, scope = User.class)
public class User {
    public enum Role {
        SPINE_ADMIN, SPINE_USER, CAREGIVER
    }

    @Id
    @GeneratedValue
    private long id;
    private String name;
    @Column(unique = true)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String organization;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Entry> entries = new ArrayList<>();
    @Column(nullable = false)
    private Role role;
    private long lastPasswordChange = System.currentTimeMillis();
}

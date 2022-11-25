package org.spine.iquestionapi.model;

import javax.persistence.*;

import org.spine.iquestionapi.service.EntityIdResolver;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

/**
 * The user of the application
 */
@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
   generator = ObjectIdGenerators.PropertyGenerator.class,
   property = "id",
   resolver = EntityIdResolver.class,
    scope = User.class
   )
public class User {
    /**
     * The roels a user can have
     */
    public enum Role {
        /** A Spine admin */
        SPINE_ADMIN,
        /** A Spine user */
        SPINE_USER,
        /** A caregiver */
        CAREGIVER
    }
    
    /**
     * The id of the user
     */
    @Id
    @GeneratedValue
    private long id;
    /**
     * The name of the user
     */
    private String name;
    /**
     * The email of the user
     */
    @Column(unique=true)
    private String email;
    /**
     * The password of the user
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    /**
     * The organization of the user
     */
    private String organization;
    /**
     * The state of activation of the account
     */
    private boolean enabled = false;
    /**
     * The entries a user filled in
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "caregiver")
    @JsonIgnore
    private List <Entry> entries = new ArrayList<>();
    /**
     * The role of the user
     */
    @Column(nullable = false)
    private Role role;

}
